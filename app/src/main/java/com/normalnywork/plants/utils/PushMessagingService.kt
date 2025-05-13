package com.normalnywork.plants.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.normalnywork.plants.MainActivity
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import splitties.experimental.ExperimentalSplittiesApi
import splitties.permissions.hasPermission
import splitties.systemservices.notificationManager

class PushMessagingService : FirebaseMessagingService(), KoinComponent {

    private val authRepository: AuthRepository by inject()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @OptIn(ExperimentalSplittiesApi::class)
    @SuppressLint("MissingPermission")
    override fun onMessageReceived(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        val contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setContentTitle(getString(R.string.tasks_notification_title))
            .setContentText(getString(R.string.tasks_notification_body))
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .build()

        createNotificationChannel()
        if (hasPermission(Manifest.permission.POST_NOTIFICATIONS)){
            with(NotificationManagerCompat.from(this)) {
                cancel(NOTIFICATION_ID)
                notify(NOTIFICATION_ID, notification)
            }
        }

        Log.i("PushMessagingService", message.data.toMap().toString())
    }

    override fun onNewToken(token: String) {
        scope.launch { authRepository.registerFcmToken(token) }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.tasks_notification_channel)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            notificationManager.createNotificationChannel(
                NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            )
        }
    }

    companion object {

        private const val NOTIFICATION_CHANNEL_ID = "task-reminders-channel"
        private const val NOTIFICATION_ID = 0
    }
}