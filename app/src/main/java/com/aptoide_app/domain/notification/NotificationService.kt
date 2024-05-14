package com.aptoide_app.domain.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.aptoide_app.R
import com.aptoide_app.presentation.MainActivity

/**
 * NotificationService is a class that provides a method to show a notification.
 * It uses the NotificationManager system service to notify the user.
 */
class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New Apps.kt")
            .setContentText("New applications available")
            .setContentIntent(activityPendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }
    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}