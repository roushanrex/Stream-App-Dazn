package com.streaming.dazn.utils

data class EventHandler(
    val onRewind: () -> Unit,
    val onPlayPauseClicked: () -> Unit,
    val onFastForward: () -> Unit
)