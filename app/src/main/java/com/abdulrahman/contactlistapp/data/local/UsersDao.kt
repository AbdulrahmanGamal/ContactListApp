package com.abdulrahman.contactlistapp.data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("SELECT * FROM UserDBEntity")
    fun getPagingSource(): PagingSource<Int, UserDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(users: List<UserDBEntity>)

    @Query("DELETE FROM UserDBEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM UserDBEntity  WHERE id=:userId LIMIT 1")
    suspend fun getUser(userId: String): UserDBEntity?

}