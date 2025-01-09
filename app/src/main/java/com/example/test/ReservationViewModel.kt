package com.example.test

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.Reservation
import com.example.test.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class ReservationViewModel(private val repository: UsersRepository) : ViewModel() {

    @SuppressLint("NewApi")
    val predefinedDays = listOf(
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2)
    )

    @SuppressLint("NewApi")
    val predefinedTimes = listOf(
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        LocalTime.of(11, 0),
        LocalTime.of(12,0),
        LocalTime.of(13,0),
        LocalTime.of(14,0),
        LocalTime.of(15,0),
        LocalTime.of(16,0),
        LocalTime.of(17,0),
        LocalTime.of(18,0),
        LocalTime.of(19,0),
        LocalTime.of(20,0),
        LocalTime.of(21,0),
        LocalTime.of(22,0)
    )
    val allSlots = predefinedDays.flatMap { day ->
        predefinedTimes.map { time ->
            Pair(day, time) // Each slot is a Pair of day and time
        }
    }

    // Reservations in the database
    val dbReservations: Flow<List<Reservation>?> = repository.getReservationsStream()

    // Reservations not in the database
    @RequiresApi(Build.VERSION_CODES.O)
    val availableSlots: Flow<List<Pair<LocalDate, LocalTime>>> = dbReservations.map { bookedReservations ->
        val bookedPairs = bookedReservations?.map { Pair(it.day, it.time) } ?: emptyList()

        // Filter out slots that are already booked or outdated
        allSlots.filter { it !in bookedPairs &&
                (it.first.isAfter(LocalDate.now()) ||
                        (it.first.isEqual(LocalDate.now())&&it.second.isAfter(LocalTime.now())))}


    }


    // Function to insert a reservation
    fun insertReservation(idUser: Int, date: LocalDate, time: LocalTime) {
        viewModelScope.launch {
            repository.insertReservation(Reservation(userId = idUser, day = date, time = time))
        }
    }


}
