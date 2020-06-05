package org.prography.lemorning.src.repository.networks

import org.prography.lemorning.src.models.Category
import retrofit2.Call
import retrofit2.http.GET

interface SearchApiService {
    @GET("/api/category")
    fun getCategory() : Call<ArrayList<Category?>>
}