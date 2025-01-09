package com.example.test

import android.app.Application
import com.example.test.data.AppDataContainer

class ReservationApplication: Application() {
    val appContainer = AppDataContainer(this)
}