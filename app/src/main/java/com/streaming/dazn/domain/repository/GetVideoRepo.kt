package com.streaming.dazn.domain.repository

import com.streaming.dazn.data.model.VideoItemDTO

interface GetVideoRepo {
    suspend fun getvideoItems(): List<VideoItemDTO>

}