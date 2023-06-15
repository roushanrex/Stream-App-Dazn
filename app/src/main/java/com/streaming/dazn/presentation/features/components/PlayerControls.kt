package com.streaming.dazn.presentation.features.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.streaming.dazn.R
import com.streaming.dazn.utils.EventHandler
import com.streaming.dazn.presentation.viewmodel.VideoPlayerViewModel


@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    currentPosition: Long,
    viewModel: VideoPlayerViewModel = viewModel(),
    onEventHandler: EventHandler
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                onEventHandler.onRewind()
                viewModel.incrementBackwardCount()
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_rewind),
                contentDescription = "Rewind"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                onEventHandler.onPlayPauseClicked()
                viewModel.incrementPauseCount()
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = if (isPlaying) {
                    painterResource(id = R.drawable.ic_pause)
                } else {
                    painterResource(id = R.drawable.ic_play)
                },
                contentDescription = if (isPlaying) "Pause" else "Play"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                onEventHandler.onFastForward()
                viewModel.incrementForwardCount()
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = "Fast Forward"
            )
        }
    }
}
