package com.streaming.dazn.data.repository


import com.streaming.dazn.data.model.VideoItemDTO
import com.streaming.dazn.domain.repository.GetVideoRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class GetVideoRepoImpl @Inject constructor() : GetVideoRepo {


    override suspend fun getvideoItems(): List<VideoItemDTO> {
        val jsonString = """
            [
                {
                    "name": "HD (MP4, H264)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"
                },
                {
                    "name": "UHD (MP4, H264)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_uhd.mpd"
                },
                {
                    "name": "HD (MP4, H265)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears.mpd"
                },
                {
                    "name": "UHD (MP4, H265)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears_uhd.mpd"
                },
                {
                    "name": "HD (WebM, VP9)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears.mpd"
                },
                {
                    "name": "UHD (WebM, VP9)",
                    "uri": "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
                }
            ]
        """

        return Gson().fromJson(jsonString, object : TypeToken<List<VideoItemDTO>>() {}.type)
    }
}