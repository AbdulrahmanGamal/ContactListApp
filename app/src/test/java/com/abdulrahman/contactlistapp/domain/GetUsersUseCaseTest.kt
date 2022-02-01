package com.abdulrahman.contactlistapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.cash.turbine.test
import com.abdulrahman.contactlistapp.PagingSourceUtils
import com.abdulrahman.contactlistapp.data.entities.Coordinates
import com.abdulrahman.contactlistapp.data.entities.Location
import com.abdulrahman.contactlistapp.data.entities.Picture
import com.abdulrahman.contactlistapp.data.local.Db
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.data.repositories.UserRepository
import com.abdulrahman.contactlistapp.data.repositories.UsersRemoteMediator
import com.abdulrahman.contactlistapp.mockedUser
import com.abdulrahman.contactlistapp.transformPagingDataToListOfData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUsersUseCaseTest {


    @ExperimentalPagingApi
    @Test
    fun `invoke() get users stream returns users`() = runBlocking {
        val repository = mock<UserRepository>()
        whenever(repository.getUsersStream()).doReturn(flowOf(PagingData.from(listOf())))

        val tested = GetUsersUseCase(repository)

        tested().test {
            awaitItem()
            awaitComplete()
        }

    }
}

