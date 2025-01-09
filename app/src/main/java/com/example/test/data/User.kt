package com.example.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val firstName: String="",
    val lastName: String="",
    val username: String="",
    val email: String="",
    val password: String=""
)