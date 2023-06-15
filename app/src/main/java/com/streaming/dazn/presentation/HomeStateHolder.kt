package com.streaming.dazn.presentation

import com.streaming.dazn.data.model.VideoItemDTO

data class HomeStateHolder(
    val isLoading:Boolean=false,
    val data:List<VideoItemDTO>?=null,
    val error:String=""

)
