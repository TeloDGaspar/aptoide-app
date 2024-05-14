package com.aptoide_app.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aptoide_app.domain.app.FullDetailApp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageApp(graphic: State<List<FullDetailApp>>, innerPadding: PaddingValues) {
    val pagerState = rememberPagerState(pageCount = { graphic.value.size })
    if (graphic.value.isEmpty()) return
    HorizontalPager(
        modifier = Modifier
            .padding(innerPadding)
            .padding(top = 10.dp)
            .fillMaxWidth(),
        state = pagerState,
        beyondBoundsPageCount = 1
    ) { page ->
        val image = graphic.value[page]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)
                .padding(start = 16.dp, end = 16.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            AsyncImage(
                model = image.graphic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}