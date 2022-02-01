package com.abdulrahman.contactlistapp.presentation.details

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import app.cash.turbine.test
import com.abdulrahman.contactlistapp.data.repositories.UserRepository
import com.abdulrahman.contactlistapp.domain.GetUserByIdUseCase
import com.abdulrahman.contactlistapp.domain.GetUsersUseCase
import com.abdulrahman.contactlistapp.mockedUser
import com.abdulrahman.contactlistapp.transformPagingDataToListOfData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ContactsDetailsViewModelTest {

    @ExperimentalPagingApi
    @Test
    fun `invoke() get users stream returns users`() = runBlocking {
        val useCase = mock<GetUserByIdUseCase>()
        whenever(useCase("1")).doReturn(mockedUser)
        val tested = ContactsDetailsViewModel(useCase)

        tested.getUser("1")
        tested.userFlow.test {
            assertEquals(mockedUser, awaitItem())
        }
    }
}