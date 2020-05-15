package org.prography.lemorning.src.repository

import org.prography.lemorning.utils.BaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainApiService {
    @GET("/test")
    fun getTest() : Call<BaseResponse>
}