package com.streaming.dazn.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streaming.dazn.data.model.VideoItemDTO
import com.streaming.dazn.domain.use_case.GetVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getVideoUseCase: GetVideoUseCase) :
    ViewModel() {

    val videoItems = mutableStateListOf<VideoItemDTO>()

    init {
        getVideoItems()
    }

    private fun getVideoItems() {
        viewModelScope.launch {
            val items = getVideoUseCase.getvideoItems()
            videoItems.addAll(items)
        }
    }
}
