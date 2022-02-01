package com.abdulrahman.contactlistapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.domain.GetUserByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContactsDetailsViewModel(
    private val getUsersUseCase: GetUserByIdUseCase,
) : ViewModel() {
     var userFlow: MutableStateFlow<UserDBEntity?> = MutableStateFlow(null)

    fun getUser(userId: String) = viewModelScope.launch {
        userFlow.value = getUsersUseCase(userId)
    }
}


