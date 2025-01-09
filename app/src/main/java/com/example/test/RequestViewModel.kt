package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.test.data.Request
import com.example.test.data.UserWithRequests
import com.example.test.data.UserWithReservations
import com.example.test.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RequestViewModel(private val repository: UsersRepository) : ViewModel() {

    // Function to insert a request
    fun insertRequest(idUser: Int, message: String) {
        viewModelScope.launch {
            repository.insertRequest(Request(userId = idUser, message = message))
        }
    }

    fun getRequestsByUserId(userId: Int): Flow<UserWithRequests> {
        return repository.getRequestsByUser(userId)
    }

    companion object {

        val Factory: Factory = object : Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return RequestViewModel(
                    (application as ReservationApplication).appContainer.itemsRepository
                ) as T
            }
        }
    }
}
