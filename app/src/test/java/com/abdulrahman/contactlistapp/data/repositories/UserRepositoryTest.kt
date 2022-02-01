package com.abdulrahman.contactlistapp.data.repositories

import androidx.paging.*
import app.cash.turbine.test
import com.abdulrahman.contactlistapp.PagingSourceUtils
import com.abdulrahman.contactlistapp.data.entities.Coordinates
import com.abdulrahman.contactlistapp.data.entities.Location
import com.abdulrahman.contactlistapp.data.entities.Picture
import com.abdulrahman.contactlistapp.data.local.Db
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.data.local.UsersDao
import com.abdulrahman.contactlistapp.transformPagingDataToListOfData
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserRepositoryTest {
    val mockedUser = UserDBEntity(
        id = "10",
        gender = ",male",
        name = "ahmed",
        location = Location("Cairo", "Cairo", "102523", Coordinates(1.0, 6.6)),
        email = "a@test.com",
        dob = "20/02/2020",
        registered = "20/02/2020",
        phone = "+2015255523",
        picture = Picture("", "", ""),
    )
   private val mockDatabase: Db = mock()
   private val usersDao: UsersDao = mock()
   private val mockUsersRemoteMediator: UsersRemoteMediator = mock()

    @ExperimentalPagingApi
    @Test
    fun `when get users stream returns users`() = runBlocking {
        val pagingDataResult = PagingSourceUtils(listOf<UserDBEntity>())
        whenever(mockDatabase.usersDao()).thenReturn(usersDao)
        whenever(usersDao.getPagingSource()).thenReturn(pagingDataResult )

        val tested = UserRepository(mockDatabase, mockUsersRemoteMediator)

        tested.getUsersStream().test {
            Assert.assertEquals(listOf<UserDBEntity>(), awaitItem().transformPagingDataToListOfData())
        }

    }

    @Test
    fun `when get user by id returns user`() = runBlocking {
        whenever(mockDatabase.usersDao()).thenReturn(usersDao)
        whenever(usersDao.getUser("1")).thenReturn(mockedUser )

        val tested = UserRepository(mockDatabase, mockUsersRemoteMediator)
        Assert.assertEquals(mockedUser, tested.getUser("1"))

    }
}