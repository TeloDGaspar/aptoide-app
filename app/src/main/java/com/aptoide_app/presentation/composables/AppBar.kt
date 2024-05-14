package com.aptoide_app.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.aptoide_app.domain.connectivity.ConnectivityObserver
import com.aptoide_app.presentation.ViewModelTest

/**
 * AppBar is a composable function that displays a top app bar with a title and actions.
 * The actions change based on the network connectivity status.
 * If the network is unavailable, it displays a "No Connectivity" text.
 * If the network is available, it displays a refresh icon button that fetches full detail app when clicked.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    viewModel: ViewModelTest,
    composable: @Composable (PaddingValues) -> Unit
) {
    val networkStatus = viewModel.network.collectAsState().value
    val isNetworkUnavailable = networkStatus == ConnectivityObserver.Status.Unavailable || networkStatus == ConnectivityObserver.Status.Lost

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF9800)),
                title = { Text("Aptoide", color = Color.White) },
                actions = {
                    if (isNetworkUnavailable) {
                        Text(text = "No Connectivity")
                    } else {
                        IconButton(onClick = { viewModel.getFullDetailApp() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Search", tint = Color.White)
                        }
                    }
                }
            )
        },
        content = composable
    )
}