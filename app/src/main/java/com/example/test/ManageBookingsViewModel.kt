package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.Reservation
import com.example.test.data.UserWithReservations
import com.example.test.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class ManageBookingsViewModel (private val repository: UsersRepository,private val userId: Int): ViewModel(){

    var bookings : Flow<UserWithReservations?> = repository.getReservationsByUser(userId)

    fun deleteReservation(reservation: Reservation) {
        viewModelScope.launch {
            repository.deleteReservation(reservation)
        }
    }

}