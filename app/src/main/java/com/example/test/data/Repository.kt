package com.example.test.data

import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getAllUsersStream(): Flow<List<User>>

    fun getUserStream(id: Int): Flow<User?>

    fun getPassword(username: String): Flow<String>

    fun getUserByUsernameStream(username: String): Flow<User?>

    fun getReservationsStream():Flow<List<Reservation>?>

    suspend fun insertUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun insertReservation(reservation: Reservation)

    suspend fun insertRequest(request: Request)

    suspend fun deleteReservation(reservation: Reservation)

    fun getReservationsByUser(userId: Int): Flow<UserWithReservations>

    fun getRequestsByUser(userId: Int): Flow<UserWithRequests>

}