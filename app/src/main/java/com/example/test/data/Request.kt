package com.example.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "requests")
data class Request (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId: Int,
    val message: String
)