package com.example.proyekpam

import android.annotation.SuppressLint
import android.app.Application


class StoryIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StoryRepository.initialize(this)
    }
}