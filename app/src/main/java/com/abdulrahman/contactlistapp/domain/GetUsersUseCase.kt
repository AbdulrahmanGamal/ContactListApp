package com.abdulrahman.contactlistapp.domain

import androidx.paging.ExperimentalPagingApi
import com.abdulrahman.contactlistapp.data.repositories.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    @ExperimentalPagingApi
    operator fun invoke() = userRepository.getUsersStream()
}