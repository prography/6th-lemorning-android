package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.repositories.UserRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.utils.Validators
import org.prography.lemorning.src.utils.objects.SingleEvent

class SignInViewModel(application: Application) : BaseViewModel(application) {
  private val userRepo = UserRepository(application)

  companion object {
    const val SIGN_IN_SUCCESS = 10
    const val SIGN_IN_FAILURE_NEED_SIGN_UP = 11
  }

  val email: MutableLiveData<String> = MutableLiveData("")
  val password: MutableLiveData<String> = MutableLiveData("")

  val navTo: MutableLiveData<SingleEvent<Int>> = MutableLiveData()

  fun onClickSignIn() {
    if (!Validators.isValidEmail(email.value) || password.value?.length == 0) {
      toastEvent.value = SingleEvent("이메일 형식을 지켜주세요.")
      return
    }
    trySignIn(email.value!!, password.value!!)
  }

  private fun trySignIn(email: String, password: String) {
    userRepo.trySignIn(email, password)
      .toDisposal(rxDisposable, {
        if (it) {
          toastEvent.value = SingleEvent("환영합니다.")
          navTo.value = SingleEvent(SIGN_IN_SUCCESS)
        } else {
          toastEvent.value = SingleEvent("로그인에 실패하였습니다.")
        }
      }, {
        doOnNetworkError(it)
      })
  }
}