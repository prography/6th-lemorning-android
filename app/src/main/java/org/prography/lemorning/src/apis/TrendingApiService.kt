package org.prography.lemorning.src.apis

import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.models.Popular
import retrofit2.Call
import retrofit2.http.GET

interface TrendingApiService {
    @GET("/api/shop")
    fun getForYous() : Call<ArrayList<ForYou?>>

    @GET("/api/shop")
    fun getPopulars() : Call<ArrayList<Popular?>>
}