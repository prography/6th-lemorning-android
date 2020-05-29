package org.prography.lemorning.src.repository

import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.utils.BaseArrayResponse
import org.prography.lemorning.utils.BaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface TrendingApiService {
    @GET("/api/board")
    fun getForYous() : Call<ArrayList<ForYou>>
}