package org.prography.lemorning.src.repository.networks

import io.reactivex.Single
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.models.Popular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInApiService {
    @POST("/rest-auth/login")
    fun getForYous() : Single<ArrayList<ForYou?>>
}