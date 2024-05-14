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
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aptoide_app.R
import com.aptoide_app.data.remote.AptoideApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

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
