package com.example.test.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRequests(
    @Embedded val user: User = User(),
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val reservations: List<Request> = listOf()
)