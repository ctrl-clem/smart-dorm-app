package com.example.test

data class UserState (
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val confirmPassword: String = "",
    val firstname : String = "",
    val surname : String = "",

    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val usernameError: String = "",
    val firstnameError : String = "",
    val surnameError : String = "",

    val isUsernameCorrect : Boolean = true,
    val isPasswordCorrect: Boolean = true,

    val userId: Int = -1

)