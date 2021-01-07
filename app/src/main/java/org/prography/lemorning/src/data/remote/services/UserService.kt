package org.prography.lemorning.src.data.remote.services

import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.prography.lemorning.ApplicationClass.Companion.noTokenRetrofit
import org.prography.lemorning.ApplicationClass.Companion.retrofit
import org.prography.lemorning.src.data.remote.apis.UserApi
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignInParam
import org.prography.lemorning.src.utils.Constants
import java.io.File

class UserService : BaseService() {
  private val userApi = retrofit.create(UserApi::class.java)
  private val noTokenUserApi = noTokenRetrofit.create(UserApi::class.java)

  fun signIn(email: String, pwd: String): Single<Jwt> {
    return noTokenUserApi.signIn(SignInParam(email, pwd))
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun signUp(email: String, password: String, profile: Uri, nickName: String, gender: String, birth: String): Single<Boolean> {
    val file = File(profile.path!!)
    return noTokenUserApi
      .signUp(email = email.toRequestBody(),
        password = password.toRequestBody(),
        profile = MultipartBody.Part.createFormData("profile", file.name, file.asRequestBody(
          Constants.MEDIA_TYPE_FORM_DATA.toMediaType())),
        nickname = nickName.toRequestBody(),
        gender = gender.toRequestBody(),
        birth = birth.toRequestBody()
      )
      .map { it.code == 200 }
      .observeOn(AndroidSchedulers.mainThread())
  }
}