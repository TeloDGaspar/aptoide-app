package com.aptoide_app.domain

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aptoide_app.R
import java.util.concurrent.TimeUnit

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val service = NotificationService(applicationContext)
        service.showNotification()
        Log.i("Devlog","work")
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createNotification(context: Context) {
        // Create notification channel (for API level >= 26)
        createNotificationChannel(context)

        // Create notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("New Apps Available")
            .setContentText("Check out the latest apps in our store!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            // Check if the app has the required permission
            val hasPermission = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                // Permission is granted, proceed with showing the notification
                notify(NOTIFICATION_ID, notification)
            } else {
                // Permission is not granted, request it from the user
                // This will prompt the user to grant the notification permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create notification channel code for Android O and above
        // Implementation omitted for brevity
    }

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
        const val CHANNEL_ID = "your_notification_channel_id"
        const val NOTIFICATION_ID = 1
    }
}
