package com.example.test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class,Reservation::class,Request::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UsersDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun reservationDao(): ReservationDao
    abstract fun requestDao(): RequestDao
    companion object{
        @Volatile
        private var Instance: UsersDatabase? = null
        fun getDatabase(context: Context): UsersDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UsersDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance=it }
            }
        }
    }
}