package com.aptoide_app.domain.notification

import android.content.Context

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * NotificationWorker is a class that extends CoroutineWorker.
 * It is used to perform background work in the application.
 * This class is responsible for showing notifications.
 */
class NotificationWorker( context: Context,  params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val service = NotificationService(applicationContext)
        service.showNotification()

        return Result.success()
    }
}
