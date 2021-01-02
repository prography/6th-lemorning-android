package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.media.MediaRecorder
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.utils.objects.SingleEvent
import java.util.concurrent.TimeUnit

class CreateSongViewModel(application: Application) : BaseViewModel(application) {
  private val songRepo = SongRepository(application)

  val profileUri: MutableLiveData<Uri?> = MutableLiveData()
  val canFinish: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

  var isRecording: Boolean? = null
  val curAmpitude: MutableLiveData<Int> = MutableLiveData()

  fun registerNewSong(
    name: String,
    amount: Int = 10,
    price: Int = 500,
    stock: Int = 50,
    alarmPath: String
  ) {
    songRepo.registerNewSong(name, amount, price, stock, alarmPath, profileUri.value!!)
      .toDisposal(rxDisposable, {
        toastEvent.value = SingleEvent("업로드에 성공했습니다.")
        canFinish.value = SingleEvent(true)
      }, {
        doOnNetworkError(it)
      })
  }

  fun registerTimerOnAudio(mediaRecorder: MediaRecorder) {
    rxDisposable.add(
      Observable.interval(0, 100, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          isRecording?.let {
            if (it) curAmpitude.value = mediaRecorder.maxAmplitude
          }
        })
  }
}