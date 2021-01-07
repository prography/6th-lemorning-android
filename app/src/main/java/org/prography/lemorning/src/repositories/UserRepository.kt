package org.prography.lemorning.src.repositories

import android.app.Application
import android.net.Uri
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.ApplicationClass.Companion.sharedPref
import org.prography.lemorning.src.data.remote.services.UserService
import org.prography.lemorning.src.utils.Constants.LAST_USER_EMAIL
import org.prography.lemorning.src.utils.Constants.LAST_USER_PWD
import org.prography.lemorning.src.utils.Constants.X_ACCESS_TOKEN

class UserRepository(application: Application) : BaseRepository(application) {
  private val userService = UserService()
  private val storedUserId = sharedPref.getString(LAST_USER_EMAIL, null)
  private val storedUserPwd = sharedPref.getString(LAST_USER_PWD, null)

  fun tryAutoSignIn(): Single<Boolean> {
    if (storedUserId == null || storedUserPwd == null)
      return Single.just(false)
    return userService.signIn(storedUserId, storedUserPwd)
      .flatMap {
        sharedPref.edit().putString(X_ACCESS_TOKEN, it.token).apply()
        Single.just(true)
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun trySignIn(email: String, pwd: String): Single<Boolean> {
    return userService.signIn(email, pwd)
      .flatMap {
        sharedPref.edit()
          .putString(X_ACCESS_TOKEN, it.token)
          .putString(LAST_USER_EMAIL, email)
          .putString(LAST_USER_PWD, pwd)
          .apply()
        Single.just(true)
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun trySignUp(email: String, pwd: String, profile: Uri, nickName: String, gender: String, birth: String): Single<Boolean> {
    return userService.signUp(email, pwd, profile, nickName, gender, birth)
  }
}