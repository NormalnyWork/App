package com.normalnywork.plants

import android.app.Application
import com.normalnywork.plants.data.di.networkModule
import com.normalnywork.plants.data.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlantsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, repositoryModule)
        }
    }
}