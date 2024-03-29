package com.g452.bindlibrary

import android.app.Application

open class App : Application() {

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