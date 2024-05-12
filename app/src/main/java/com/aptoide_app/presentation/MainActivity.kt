package com.aptoide_app.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.AsyncImage
import com.aptoide_app.domain.FullDetailApp
import com.aptoide_app.domain.NotificationWorker
import com.aptoide_app.domain.SimplifiedApp
import com.aptoide_app.themes.MyTheme
import dagger.hilt.android.AndroidEntryPoint

import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(applicationContext)

        val request = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 15, // Repeat interval in hours
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()
        workManager.enqueueUniquePeriodicWork(
            "NotificationWorkerPeriodicWork", ExistingPeriodicWorkPolicy.UPDATE, request
        )
        setContent {
            val viewModel: ViewModelTest = hiltViewModel()
            val graphic = viewModel.fullDetailApp.collectAsState()
            MyTheme() {
                SmallTopAppBarExample() { innerPadding ->
                    Column {
                        HorizontalLoader(graphic = graphic, innerPadding = innerPadding)
                        Spacer(modifier = Modifier.width(16.dp))
                        teste(viewModel = viewModel, innerPadding = innerPadding)
                    }

                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalLoader(graphic: State<List<FullDetailApp>>, innerPadding: PaddingValues) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    if(graphic.value.isEmpty()) return
    HorizontalPager(
        modifier = Modifier.padding(innerPadding),
        state = pagerState,
        beyondBoundsPageCount = 1
    ) { page ->
        val image = graphic.value[page]
        AsyncImage(
            model = image.graphic,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                )
        )
    }



    /*LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
    ) {

        itemsIndexed(graphic.value) { index, imageResId ->
            AsyncImage(
                model = imageResId.graphic,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 400.dp, height = 200.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            )
        }
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(
    composable: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFF9800)
            ), title = {
                Text("Aptoide", color = Color.White)
            })
        },
    ) { innerPadding ->
        composable(innerPadding)
    }
}

@Composable
fun teste(
    viewModel: ViewModelTest, innerPadding: PaddingValues
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val test = viewModel.simplifiedApp.collectAsState()

    if (test.value.isEmpty()) {
        // Show loading indicator
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        return
    } else {
        Log.i("Devlog2", "$test")
        val fullDetailApp = viewModel.fullDetailApp.collectAsState()
        val (showPopup, setShowPopup) = remember { mutableStateOf(false) }
        val (showPopupButton, setShowPopupButton) = remember { mutableStateOf(false) }
        val (selectedApp, setSelectedApp) = remember { mutableStateOf<SimplifiedApp?>(null) }
        if (showPopup) {
            val selectedAppFull =
                fullDetailApp.value.find { it.name == (selectedApp?.name) } ?: return
            CustomPopup(selectedAppFull, onDismissRequest = {
                setShowPopup(false)
            })
        }
        if (showPopupButton) DownloadDialog(onDismissRequest = {setShowPopupButton(false)})
        LazyColumn() {
            items(test.value.size) { appIndex ->
                val app = test.value[appIndex]
                ImageItem(app = app,
                    onRowClick = { setSelectedApp(app); setShowPopup(true) },
                    onButtonClick = {setShowPopupButton(true)})
            }
        }
    }


}

@Composable
fun ImageItem(app: SimplifiedApp, onRowClick: () -> Unit, onButtonClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onRowClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = app.icon, contentDescription = null, modifier = Modifier.size(100.dp).clip(
                RoundedCornerShape(20.dp)
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = app.name, modifier = Modifier.padding(top = 8.dp)
            )
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(50.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Download", style = TextStyle(
                        fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                )
            }
        }
    }
}


@Composable
fun CustomPopup(selectedApp: FullDetailApp, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = selectedApp.graphic,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(20.dp))
                )
                FullDetailAppInfo(selectedApp)
                Button(onClick = onDismissRequest) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Composable
fun DownloadDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Download functionality is not available in demo mode.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        }
    }
}


@Composable
fun FullDetailAppInfo(fullDetailApp: FullDetailApp) {
    Column(
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Name: ${fullDetailApp.name}", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Added: ${fullDetailApp.added}")
        Text(text = "APK Tags: ${fullDetailApp.apkTags}")
        Text(text = "Downloads: ${fullDetailApp.downloads}")
        Text(text = "ID: ${fullDetailApp.id}")
        Text(text = "Modified: ${fullDetailApp.modified}")
        Text(text = "Package: ${fullDetailApp.packageX}")
        Text(text = "PDownloads: ${fullDetailApp.pdownloads}")
        Text(text = "Rating: ${fullDetailApp.rating}")
        Text(text = "Size: ${fullDetailApp.size}")
        Text(text = "Store ID: ${fullDetailApp.storeId}")
        Text(text = "Store Name: ${fullDetailApp.storeName}")
        Text(text = "Updated: ${fullDetailApp.updated}")
        Text(text = "Uptype: ${fullDetailApp.uptype}")
        Text(text = "Version Code: ${fullDetailApp.vercode}")
        Text(text = "Version Name: ${fullDetailApp.vername}")
        Spacer(modifier = Modifier.height(16.dp))
    }
}