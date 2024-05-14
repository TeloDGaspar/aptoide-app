package com.aptoide_app.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aptoide_app.domain.FullDetailApp
import com.aptoide_app.presentation.ViewModelTest

@Composable
fun AppColumn(
    viewModel: ViewModelTest, innerPadding: PaddingValues
) {
    val test = viewModel.fullDetailApp.collectAsState()
    Log.i("test","MainActivity ${test.value}")
    if (test.value.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        return
    } else {
        val fullDetailApp = viewModel.fullDetailApp.collectAsState()
        val (showPopup, setShowPopup) = remember { mutableStateOf(false) }
        val (showPopupButton, setShowPopupButton) = remember { mutableStateOf(false) }
        val (selectedApp, setSelectedApp) = remember { mutableStateOf<FullDetailApp?>(null) }
        if (showPopup) {
            val selectedAppFull =
                fullDetailApp.value.find { it.name == (selectedApp?.name) } ?: return
            InfoDialog(selectedAppFull, onDismissRequest = {
                setShowPopup(false)
            })
        }


        if (showPopupButton) DownloadDialog(onDismissRequest = { setShowPopupButton(false) })

        LazyColumn {
            items(test.value.size) { appIndex ->
                val app = test.value[appIndex]
                AppItem(app = app,
                    onRowClick = { setSelectedApp(app); setShowPopup(true) },
                    onButtonClick = { setShowPopupButton(true) })
            }
        }
    }
}