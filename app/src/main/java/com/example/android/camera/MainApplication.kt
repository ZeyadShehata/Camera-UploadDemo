package com.example.android.camera

import android.app.Application
import com.example.android.camera.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(this@MainApplication)
            modules(androidModule)
            // ...
        }
    }

}