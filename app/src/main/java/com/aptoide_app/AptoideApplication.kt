/**
 * Copyright (c), BMW Critical TechWorks. All rights reserved.
 */
package com.aptoide_app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.aptoide_app.domain.notification.NotificationService
import dagger.hilt.android.HiltAndroidApp

/**
 * It presents the application implementation
 * */
@HiltAndroidApp
class AptoideApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.COUNTER_CHANNEL_ID,
            "Applications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "New Applications available"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
