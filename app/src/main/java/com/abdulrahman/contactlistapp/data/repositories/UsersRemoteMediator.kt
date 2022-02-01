package com.abdulrahman.contactlistapp.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.abdulrahman.contactlistapp.data.local.Db
import com.abdulrahman.contactlistapp.data.local.RemoteKeys
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.data.mapper.UserMapper
import com.abdulrahman.contactlistapp.data.remote.UsersWebService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator(
    private val service: UsersWebService,
    private val database: Db,
    private val mapper: UserMapper,
) : RemoteMediator<Int, UserDBEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDBEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    1
                }
                remoteKeys?.nextKey ?: 1
            }
            else -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
        try {
            val apiResponse = service.getUsers(page = page, results = state.config.pageSize)
            val endOfPaginationReached = apiResponse.results.isEmpty()
            val mappedUsers = mapper.transformToList(apiResponse.results)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.usersDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = mappedUsers.map {
                    RemoteKeys(userId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.usersDao().save(mappedUsers)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserDBEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                database.remoteKeysDao().remoteKeysUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserDBEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                database.remoteKeysDao().remoteKeysUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserDBEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                database.remoteKeysDao().remoteKeysUserId(userId)
            }
        }
    }

}