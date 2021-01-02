package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.R
import org.prography.lemorning.src.repositories.UserRepository
import org.prography.lemorning.src.utils.Constants
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.utils.Validators
import org.prography.lemorning.src.utils.objects.SingleEvent

class SignUpViewModel(application: Application) : BaseViewModel(application) {
  private val userRepo = UserRepository(application)

  var navTo: MutableLiveData<SingleEvent<Int>> = MutableLiveData()

  val email: MutableLiveData<String> = MutableLiveData("")
  val emailStatus: MutableLiveData<Int> = MutableLiveData(Constants.EMAIL_EMPTY)
  val password: MutableLiveData<String> = MutableLiveData("")
  val passwordRe: MutableLiveData<String> = MutableLiveData("")
  val profileUri: MutableLiveData<Uri?> = MutableLiveData()
  val gender: MutableLiveData<String> = MutableLiveData(Constants.MALE)
  val birth: MutableLiveData<String> = MutableLiveData()
  val nickname: MutableLiveData<String> = MutableLiveData("")

  val agreeAll: MutableLiveData<Boolean> = MutableLiveData(false)
  val agreeFirst: MutableLiveData<Boolean> = MutableLiveData(false)
  val agreeSecond: MutableLiveData<Boolean> = MutableLiveData(false)
  val agreeThird: MutableLiveData<Boolean> = MutableLiveData(false)

  fun onClickValidateEmail() {
    emailStatus.value = if (Validators.isValidEmail(email.value)) Constants.EMAIL_VALID else Constants.EMAIL_INVALID
  }

  fun onClickGender(idx: Int) {
    gender.value = when (idx) {
      0 -> Constants.MALE
      else -> Constants.FEMALE
    }
  }

  fun onClickAgreement(idx: Int, old: Boolean) {
    when (idx) {
      0 -> {
        agreeAll.value = !old
        agreeFirst.value = !old
        agreeSecond.value = !old
        agreeThird.value = !old
      }
      1 -> {
        agreeFirst.value = !old
        agreeAll.value = agreeFirst.value!! && agreeSecond.value!!
      }
      2 -> {
        agreeSecond.value = !old
        agreeAll.value = agreeFirst.value!! && agreeSecond.value!!
      }
      3 -> {
        agreeThird.value = !old
      }
    }
  }

  fun onClickNext(position: Int) {
    when (position) {
      0 -> {
        if (emailStatus.value != Constants.EMAIL_VALID) {
          toastEvent.value = SingleEvent("이메일을 확인해주세요.")
          return
        }
        if (Validators.isValidPasswords(
            password.value!!,
            passwordRe.value!!
          ) != Constants.PASSWORD_SAME
        ) {
          toastEvent.value = SingleEvent("비밀번호를 확인해주세요.")
          return
        }
        navTo.value = SingleEvent(R.id.action_to_step2)
      }
      1 -> {
        if (profileUri.value == null) {
          toastEvent.value = SingleEvent("프로필 사진을 선택해주세요.")
          return
        }
        if (nickname.value!!.isEmpty()) {
          toastEvent.value = SingleEvent("닉네임을 확인해주세요.")
          return
        }
        if (birth.value!!.isEmpty() && Validators.isValidBirth(birth.value!!)) {
          toastEvent.value = SingleEvent("생년월일을 선택해주세요.")
          return
        }
        navTo.value = SingleEvent(R.id.action_to_step3)
      }
      2 -> {
        if (!agreeAll.value!!) {
          toastEvent.value = SingleEvent("필수 약관에 모두 동의해주세요.")
          return
        }
        tryRegisterUser()
      }
      3 -> navTo.value = SingleEvent(-1)
    }
  }

  private fun tryRegisterUser() {
    if (email.value == null || password.value == null || profileUri.value == null || nickname.value == null || gender.value == null || birth.value == null) {
      return
    }
    networkEvent.start()
    userRepo.trySignUp(
      email.value!!,
      password.value!!,
      profileUri.value!!,
      nickname.value!!,
      gender.value!!,
      birth.value!!
    )
      .toDisposal(rxDisposable, {
        networkEvent.done()
        if (it) {
          navTo.value = SingleEvent(R.id.action_to_step4)
        } else {
          toastEvent.value = SingleEvent("회원가입에 실패하였습니다.")
        }
      }, {
        doOnNetworkError(it)
      })
  }

}