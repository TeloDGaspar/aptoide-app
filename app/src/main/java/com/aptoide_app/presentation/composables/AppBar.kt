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
import com.aptoide_app.domain.ConnectivityObserver
import com.aptoide_app.presentation.ViewModelTest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    viewModel: ViewModelTest,
    composable: @Composable (PaddingValues) -> Unit
) {
    val networkValue = viewModel.network.collectAsState().value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFF9800)
            ), title = {
                Text("Aptoide", color = Color.White)
            }, actions = {
                if (networkValue != ConnectivityObserver.Status.Available) {
                    Text(text = "No Connectivity")
                    return@CenterAlignedTopAppBar
                }
                IconButton(onClick = { viewModel.getFullDetailApp() }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            })
        },
    ) { innerPadding ->
        composable(innerPadding)
    }
}