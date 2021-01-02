package org.prography.lemorning.src.data.remote.apis

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignInParam
import org.prography.lemorning.src.utils.objects.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi {

  @Multipart
  @POST("/signup")
  fun signUp(@Part("email") email: RequestBody?,
             @Part("password") password: RequestBody?,
             @Part profile: MultipartBody.Part?,
             @Part("account.nickname") nickname: RequestBody?,
             @Part("account.sex") gender: RequestBody?,
             @Part("account.birth") birth: RequestBody?
  ): Single<BaseResponse<Any>>

  @POST("/login")
  fun signIn(@Body param: SignInParam): Single<Jwt>
}