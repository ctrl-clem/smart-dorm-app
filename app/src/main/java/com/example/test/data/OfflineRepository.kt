package com.example.test.data

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao, private val reservationDao: ReservationDao, private val requestDao: RequestDao) : UsersRepository {
    override fun getUserByUsernameStream(username: String): Flow<User?> = userDao.getUserByUsername(username)

    override fun getPassword(username: String): Flow<String> = userDao.getPassword(username)

    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override fun getReservationsStream(): Flow<List<Reservation>?> = reservationDao.getReservations()

    override suspend fun insertReservation(reservation: Reservation) = reservationDao.insertReservation(reservation)

    override fun getReservationsByUser(userId: Int): Flow<UserWithReservations> = userDao.getUserWithReservations(userId)

    override fun getRequestsByUser(userId: Int) : Flow<UserWithRequests> = userDao.getUserWithRequests(userId)

    override suspend fun deleteReservation(reservation: Reservation) = reservationDao.deleteReservation(reservation)

    override suspend fun insertRequest(request: Request) = requestDao.insertRequest(request)

}