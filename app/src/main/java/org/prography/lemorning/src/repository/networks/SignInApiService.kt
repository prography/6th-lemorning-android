package org.prography.lemorning.src.repository.networks

import io.reactivex.Single
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignInParam
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApiService {
    @POST("/rest-auth/login")
    fun postToken(@Body param : SignInParam) : Single<Jwt>
}