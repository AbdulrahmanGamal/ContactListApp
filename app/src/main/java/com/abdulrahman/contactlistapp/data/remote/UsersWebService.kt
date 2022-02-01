package com.abdulrahman.contactlistapp.data.remote

import com.abdulrahman.contactlistapp.core.ListResponse
import com.abdulrahman.contactlistapp.data.entities.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersWebService {

    @GET("/api")
    suspend fun getUsers(
        @Query("seed") seed: String = "abc",
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
    ): ListResponse<User>

}