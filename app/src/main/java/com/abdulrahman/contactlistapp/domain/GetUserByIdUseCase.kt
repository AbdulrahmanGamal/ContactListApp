package com.abdulrahman.contactlistapp.domain

import com.abdulrahman.contactlistapp.data.repositories.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id:String) = userRepository.getUser(id)
}