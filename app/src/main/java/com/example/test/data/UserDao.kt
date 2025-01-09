package com.example.test.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Query("SELECT * from users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT password from users WHERE username = :username")
    fun getPassword(username: String): Flow<String>

    @Query("SELECT * from users WHERE username = :username ")
    fun getUserByUsername(username:String): Flow<User?>

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithReservations(): Flow<List<UserWithReservations>>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithReservations(userId: Int): Flow<UserWithReservations>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithRequests(userId: Int): Flow<UserWithRequests>

}