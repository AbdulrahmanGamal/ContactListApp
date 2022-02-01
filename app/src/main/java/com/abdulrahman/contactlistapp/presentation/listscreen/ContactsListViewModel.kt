package com.abdulrahman.contactlistapp.presentation.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abdulrahman.contactlistapp.data.local.UserDBEntity
import com.abdulrahman.contactlistapp.domain.GetUsersUseCase
import kotlinx.coroutines.flow.Flow

class ContactsListViewModel(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {
    private var usersFlow: Flow<PagingData<UserDBEntity>>? = null


    @ExperimentalPagingApi
    fun observeUsers(): Flow<PagingData<UserDBEntity>> {
        val lastResult = usersFlow
        if ( lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<UserDBEntity>> = getUsersUseCase()
            .cachedIn(viewModelScope)
        usersFlow = newResult
        return newResult
    }
}


