package org.prography.lemorning

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.prography.lemorning.src.utils.objects.SingleEvent
import org.prography.lemorning.src.utils.components.NetworkEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

  protected val rxDisposable = CompositeDisposable()

  val networkEvent = NetworkEvent()
  val toastEvent : MutableLiveData<SingleEvent<String>> = MutableLiveData()
  val alertEvent : MutableLiveData<SingleEvent<String>> = MutableLiveData()

  override fun onCleared() {
    super.onCleared()
    if (!rxDisposable.isDisposed) rxDisposable.dispose()
  }

  open fun doOnNetworkError(error: Throwable?) {
    error?.printStackTrace()
    networkEvent.done()
    alertEvent.value = SingleEvent(error?.message ?: "알 수 없는 오류입니다.")
  }
}