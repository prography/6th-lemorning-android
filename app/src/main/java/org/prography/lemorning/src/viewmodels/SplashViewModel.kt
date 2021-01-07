package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.repositories.UserRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.utils.objects.SingleEvent
import java.util.concurrent.TimeUnit

class SplashViewModel(application: Application) : BaseViewModel(application) {

  private val userRepo = UserRepository(application)

  val successLogin: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

  fun tryAutoSignIn() {
    userRepo.tryAutoSignIn().toDisposal(rxDisposable, {
      successLogin.value = SingleEvent(it)
    }, {
      successLogin.value = SingleEvent(false)
    })
  }

  init {
    rxDisposable.add(Observable.timer(500, TimeUnit.MILLISECONDS)
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe {
        tryAutoSignIn()
      })
  }
}