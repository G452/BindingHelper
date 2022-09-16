package com.g452.bindlibrary

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var context: App
            private set
    }
}