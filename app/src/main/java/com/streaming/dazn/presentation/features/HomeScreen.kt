package com.streaming.dazn.presentation.features

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.streaming.dazn.data.model.VideoItemDTO
import com.streaming.dazn.presentation.viewmodel.HomeViewModel


@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val videoItems = homeViewModel.videoItems

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Stream App DAZN ") },
                backgroundColor = Color.Blue, // Customize the background color
                contentColor = Color.White // Customize the content color (text, icons, etc.)
            )
        }
    ) {
        if (videoItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn {
                items(videoItems) { videoItem ->
                    VideoItem(videoItem) {
                        navController.navigate("videoPlayer/${Uri.encode(videoItem.uri)}")
                    }
                }
            }
        }
    }
}


@Composable
fun VideoItem(videoItem: VideoItemDTO, onItemClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .clickable(onClick = onItemClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = videoItem.name,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(10 / 5f)
                    .background(Color.LightGray)
            ) {

            }
        }
    }
}
