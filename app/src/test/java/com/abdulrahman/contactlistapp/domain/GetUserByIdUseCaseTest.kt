package com.abdulrahman.contactlistapp.domain

import com.abdulrahman.contactlistapp.data.repositories.UserRepository
import com.abdulrahman.contactlistapp.mockedUser
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserByIdUseCaseTest{


    @Test
    fun `invoke() get user by id returns user`() = runBlocking {
         val userRepository: UserRepository = mock()

        whenever(userRepository.getUser("1")).thenReturn(mockedUser )

        val tested = GetUserByIdUseCase(userRepository)
        Assert.assertEquals(mockedUser, tested("1"))

    }
}