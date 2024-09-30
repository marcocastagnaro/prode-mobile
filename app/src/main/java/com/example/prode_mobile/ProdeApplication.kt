package com.example.prode_mobile

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class ProdeApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: ProdeApplication? = null

        val context: Context
            get() = instance!!.applicationContext
    }
}