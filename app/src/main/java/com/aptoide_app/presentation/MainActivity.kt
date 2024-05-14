package com.aptoide_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aptoide_app.domain.NotificationWorker
import com.aptoide_app.presentation.composables.AppBar
import com.aptoide_app.presentation.composables.AppColumn
import com.aptoide_app.presentation.composables.ImageApp
import com.aptoide_app.themes.MyTheme
import dagger.hilt.android.AndroidEntryPoint

import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManagerNotification()

        setContent {
            val viewModel: ViewModelTest = hiltViewModel()
            val graphic = viewModel.fullDetailApp.collectAsState()
            MyTheme {
                AppBar(viewModel) { innerPadding ->
                    Column {
                        ImageApp(graphic = graphic, innerPadding = innerPadding)
                        Spacer(modifier = Modifier.width(16.dp))
                        AppColumn(viewModel = viewModel, innerPadding = innerPadding)
                    }
                }
            }
        }
    }

    private fun workManagerNotification() {
        workManager = WorkManager.getInstance(applicationContext)

        val request = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 30,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()
        workManager.enqueueUniquePeriodicWork(
            "NotificationWorkerPeriodicWork", ExistingPeriodicWorkPolicy.UPDATE, request
        )
    }
}










