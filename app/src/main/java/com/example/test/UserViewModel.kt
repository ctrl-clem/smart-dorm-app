package com.example.test


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.Reservation
import com.example.test.data.User
import com.example.test.data.UserWithReservations
import com.example.test.data.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val usersRepository: UsersRepository): ViewModel() {

    var id by mutableStateOf(-1)
    var email by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var firstname by mutableStateOf("")
    var surname by mutableStateOf("")
        private set
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun updateFirstname(updated: String) {
        _userState.update { currentState ->
            currentState.copy(firstname = updated, firstnameError = "")
        }

        firstname = updated
    }

    fun updateSurname(updated: String) {
        _userState.update { currentState ->
            currentState.copy(surname = updated, surnameError = "")
        }

        surname = updated
    }

    fun updateUsername(updated: String) {
        _userState.update { currentState ->
            currentState.copy(username = updated, usernameError = "")
        }

        username = updated
    }

    fun updatePassword(updated: String) {
        _userState.update { currentState ->
            currentState.copy(password = updated, passwordError = "")
        }

        password = updated
    }

    fun updateConfirmPassword(updated: String) {
        _userState.update { currentState ->
            currentState.copy(confirmPassword = updated, confirmPasswordError = "")
        }

        confirmPassword = updated
    }

    fun updateEmail(updated: String) {
        _userState.update { currentState ->
            currentState.copy(email = updated, emailError = "")
        }

        email = updated
    }


    fun checkUsernameExists(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val userExists = usersRepository.getAllUsersStream()
                .map { userList -> userList.any { it.username == username } }
                .first()
            if (userExists)
                _userState.update { currentState ->
                    currentState.copy(
                        isUsernameCorrect = userExists,
                        usernameError = "Username already exists!"
                    )
                }
            callback(userExists)
        }
    }


    fun isSingUpFormValid(): Boolean {
        var isValid = true
        if (email.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(emailError = "Required field")
            }
            isValid = false
        }

        if (password.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(passwordError = "Required field")
            }
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(confirmPasswordError = "Required field")
            }
            isValid = false
        }
        if (password != confirmPassword) {
            _userState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            isValid = false
        }

        if (firstname.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(firstnameError = "Required field")
            }
            isValid = false
        }

        if (surname.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(surnameError = "Required field")
            }
            isValid = false
        }

        if (username.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(usernameError = "Required field")
            }
            isValid = false
        }

        return isValid
    }

    fun isLoginFormValid(): Boolean {
        var isValid = true
        if (username.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(usernameError = "Required field")
            }
            isValid = false
        }
        if (password.isEmpty()) {
            _userState.update { currentState ->
                currentState.copy(passwordError = "Required field")
            }
            isValid = false
        }


        return isValid

    }

    fun addUser() {
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.insertUser(
                User(
                    firstName = firstname,
                    lastName = surname,
                    email = email,
                    password = password,
                    username = username
                )
            )
        }
    }


    fun validateLoginInput(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usernameExists = usersRepository.getAllUsersStream()
                .map { userList -> userList.any { it.username == userState.value.username } }
                .first()

            val passwordCorrect = usersRepository.getAllUsersStream()
                .map { userList ->
                    userList.any {
                        it.username == userState.value.username && it.password == userState.value.password
                    }
                }
                .first()

            _userState.update { currentState ->
                currentState.copy(
                    isUsernameCorrect = usernameExists,
                    isPasswordCorrect = passwordCorrect
                )
            }

            onResult(usernameExists && passwordCorrect)
        }
    }

    fun login() {
        viewModelScope.launch {
            usersRepository.getUserByUsernameStream(username).collect { user ->
                if (user != null) {
                    _userState.update { currentState ->
                        currentState.copy(
                            firstname = user.firstName,
                            surname = user.lastName,
                            email = user.email,
                            username = user.username,
                            password = user.password,
                            userId = user.id
                        )
                    }
                } else {
                    // Handle user not found case
                    _userState.update { currentState ->
                        currentState.copy(
                            firstname = "",
                            surname = "",
                            email = "",
                            username = "",
                            password = "",
                            usernameError = "User not found",
                            userId = -1
                        )
                    }
                }
            }
        }
    }






}

