package org.prography.lemorning.src.repository.networks

import org.prography.lemorning.src.models.ForYou
import retrofit2.Call
import retrofit2.http.GET

interface TrendingApiService {
    @GET("/api/board")
    fun getForYous() : Call<ArrayList<ForYou>>
}