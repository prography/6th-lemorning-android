package org.prography.lemorning.src.apis

import io.reactivex.Single
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignUpParam
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApiService {
    @POST("/signup")
    fun postUser(@Body param : SignUpParam) : Single<Jwt>
}