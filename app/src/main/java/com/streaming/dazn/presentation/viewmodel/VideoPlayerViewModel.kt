package com.streaming.dazn.presentation.viewmodel

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor() : ViewModel() {
    private val _pauseCount = mutableStateOf(0)
    val pauseCount: State<Int> = _pauseCount

    private val _forwardCount = mutableStateOf(0)
    val forwardCount: State<Int> = _forwardCount

    private val _backwardCount = mutableStateOf(0)
    val backwardCount: State<Int> = _backwardCount

    private val _isPlaying = mutableStateOf(false)
    val isPlaying: State<Boolean> = _isPlaying

    private val _currentPosition = mutableStateOf(0L)
    val currentPosition: State<Long> = _currentPosition

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        Firebase.analytics
    }


    fun incrementPauseCount() {
        _pauseCount.value++
        sendAnalyticsEvent("PauseCount", _pauseCount.value)
    }

    fun incrementForwardCount() {
        _forwardCount.value++
        sendAnalyticsEvent("ForwardCount", _forwardCount.value)
    }

    fun incrementBackwardCount() {
        _backwardCount.value++
        sendAnalyticsEvent("BackwardCount", _backwardCount.value)
    }

    fun setIsPlaying(playing: Boolean) {
        _isPlaying.value = playing
    }

    fun setCurrentPosition(position: Long) {
        _currentPosition.value = position
    }

    private fun sendAnalyticsEvent(eventName: String, count: Int) {
        val params = Bundle().apply {
            putInt("Count", count)
        }
        firebaseAnalytics.logEvent(eventName, params)
    }
}
