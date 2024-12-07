package com.example.gardenguru
import android.app.Application

class GuruApplication :  Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}