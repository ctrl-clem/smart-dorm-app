package com.example.test.data

import android.content.Context

interface AppContainer {
    val itemsRepository: UsersRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: UsersRepository by lazy {
        OfflineUsersRepository(UsersDatabase.getDatabase(context).userDao(),
            UsersDatabase.getDatabase(context).reservationDao(),
            UsersDatabase.getDatabase(context).requestDao())
    }
}