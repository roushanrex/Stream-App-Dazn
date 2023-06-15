import android.content.Context
import android.net.Uri
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.streaming.dazn.utils.EventHandler
import com.streaming.dazn.presentation.features.components.PlayerControls
import com.streaming.dazn.presentation.viewmodel.VideoPlayerViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoPlayerScreen(
    navController: NavHostController,
    videoUri: String?,
    viewModel: VideoPlayerViewModel = viewModel()
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    LaunchedEffect(videoUri) {
        videoUri?.let { uri ->
            val mediaSource = buildMediaSource(context, Uri.parse(uri))
            exoPlayer.setMediaSource(mediaSource)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }


    val swipeableState = rememberSwipeableState(0)
    val swipeThreshold = 0.5f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    -1f to -1,
                    0f to 0,
                    1f to 1
                ),
                thresholds = { _, _ -> FractionalThreshold(swipeThreshold) },
                orientation = Orientation.Horizontal
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) },
        verticalArrangement = Arrangement.Top
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PlayerControls(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            isPlaying = viewModel.isPlaying.value,
            currentPosition = viewModel.currentPosition.value,
            onEventHandler = EventHandler(
                onRewind = {
                    exoPlayer.seekTo(exoPlayer.currentPosition - 5000)
                    viewModel.incrementBackwardCount()
                },
                onPlayPauseClicked = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                        viewModel.incrementPauseCount()
                    } else {
                        exoPlayer.play()
                    }
                    viewModel.setIsPlaying(exoPlayer.isPlaying)
                },
                onFastForward = {
                    exoPlayer.seekTo(exoPlayer.currentPosition + 5000)
                    viewModel.incrementForwardCount()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pause count: ${viewModel.pauseCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forward count: ${viewModel.forwardCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Backward count: ${viewModel.backwardCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )

        val swipeableScope = rememberCoroutineScope()
        val currentPage = remember { mutableStateOf(0) }

        val numScreens = 3


        LaunchedEffect(swipeableState) {
            snapshotFlow { swipeableState.targetValue }
                .collect { value ->
                    if (value < 0) {
                        if (currentPage.value < numScreens - 1) {
                            currentPage.value += 1
                            navController.popBackStack()
                        }
                    } else if (value > 0) {
                        if (currentPage.value > 0) {
                            currentPage.value -= 1
                            navController.popBackStack()
                        }
                    }
                }
        }

    }
}


fun buildMediaSource(context: Context, uri: Uri): MediaSource {
    val dataSourceFactory = DefaultDataSourceFactory(context, "user-agent")
    return DashMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri)
}
