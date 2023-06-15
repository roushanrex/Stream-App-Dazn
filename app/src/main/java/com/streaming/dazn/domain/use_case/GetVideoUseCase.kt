package com.streaming.dazn.domain.use_case


import com.streaming.dazn.data.model.VideoItemDTO
import com.streaming.dazn.domain.repository.GetVideoRepo
import javax.inject.Inject

class GetVideoUseCase @Inject constructor(private val getVideoRepo: GetVideoRepo) {

    suspend fun getvideoItems(): List<VideoItemDTO> {
        return getVideoRepo.getvideoItems()
    }

}