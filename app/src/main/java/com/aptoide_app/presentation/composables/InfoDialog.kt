package com.aptoide_app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.aptoide_app.domain.FullDetailApp

@Composable
fun InfoDialog(selectedApp: FullDetailApp, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = selectedApp.graphic,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                FullDetailAppInfo(selectedApp)
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Composable
fun FullDetailAppInfo(fullDetailApp: FullDetailApp) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = fullDetailApp.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Added: ${fullDetailApp.added}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Downloads: ${fullDetailApp.downloads}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Modified: ${fullDetailApp.modified}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Package: ${fullDetailApp.packageX}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Size: ${fullDetailApp.size}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Store ID: ${fullDetailApp.storeId}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Store Name: ${fullDetailApp.storeName}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Updated: ${fullDetailApp.updated}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Uptype: ${fullDetailApp.uptype}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Version Code: ${fullDetailApp.vercode}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Version Name: ${fullDetailApp.vername}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}