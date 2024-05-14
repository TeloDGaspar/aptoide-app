package com.aptoide_app.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aptoide_app.domain.app.FullDetailApp
import com.aptoide_app.presentation.ViewModelTest

/**
 * AppColumn is a composable function that displays a list of apps.
 * It fetches the app details from the ViewModel and displays them in a LazyColumn.
 * If the app details are not yet available, it displays a CircularProgressIndicator.
 * When an app item is clicked, it opens an InfoDialog with the details of the selected app.
 * When the download button of an app item is clicked, it opens a DownloadDialog.
 */
@Composable
fun AppColumn(
    viewModel: ViewModelTest
) {
    val fullDetailApp = viewModel.fullDetailApp.collectAsState()
    if (fullDetailApp.value.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        return
    } else {
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
            items(fullDetailApp.value.size) { appIndex ->
                val app = fullDetailApp.value[appIndex]
                AppItem(app = app,
                    onRowClick = { setSelectedApp(app); setShowPopup(true) },
                    onButtonClick = { setShowPopupButton(true) })
            }
        }
    }
}