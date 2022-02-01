package com.abdulrahman.contactlistapp.data.repositories

import androidx.paging.*
import com.abdulrahman.contactlistapp.data.local.Db
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import kotlinx.coroutines.flow.Flow


class UserRepository(
    private val database: Db,
    private val usersRemoteMediator: UsersRemoteMediator
) {

    @ExperimentalPagingApi
    fun getUsersStream(): Flow<PagingData<UserDBEntity>> {
        val pagingSourceFactory = { database.usersDao().getPagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = usersRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getUser(id: String) = database.usersDao().getUser(id)


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}