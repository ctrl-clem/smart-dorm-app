package com.example.test.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithReservations(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val reservations: List<Reservation>
)