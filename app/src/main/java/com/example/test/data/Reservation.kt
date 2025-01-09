package com.example.test.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reservations")
data class Reservation (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId: Int,
    val day: LocalDate,
    val time: LocalTime

)


