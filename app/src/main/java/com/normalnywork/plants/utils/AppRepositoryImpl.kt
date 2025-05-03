package com.normalnywork.plants.utils

import android.os.Handler
import android.os.Looper
import com.normalnywork.plants.MainActivity
import com.normalnywork.plants.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {

    override fun restart() {
        Handler(Looper.getMainLooper()).post {
            MainActivity.instance?.recreate()
        }
    }
}