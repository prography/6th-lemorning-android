package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.views.adapters.PlayRecommendAdapter
import java.util.concurrent.TimeUnit

class SongDetailViewModel(songId: Int, application: Application) : BaseViewModel(application) {
  private val songRepo = SongRepository(application)

  val playRecommendAdapter = PlayRecommendAdapter(this)

  val songDetail: MutableLiveData<SongDetail> = MutableLiveData()

  val nextSongList: MutableLiveData<List<SongDetail>> = MutableLiveData()

  var mediaPlayer: MutableLiveData<MediaPlayer?> = MutableLiveData()
  val curTime: MutableLiveData<Int> = MutableLiveData(0)

  fun getSongDetail(songNo: Int) {
    songRepo.loadSongDetail(songNo).toDisposal(rxDisposable, {
      songDetail.value = it
      mediaPlayer.value = MediaPlayer().apply {
        setDataSource(it.musicUrl)
        setScreenOnWhilePlaying(true)
        isLooping = true
        prepareAsync() // might take long! (for buffering, etc)
      }
    }, {
      doOnNetworkError(it)
    })
  }


  fun registerTimerOnMediaPlayer(mediaPlayer: MediaPlayer) {
    val max = mediaPlayer.duration
    rxDisposable.add(
      Observable.interval(0, 100, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { if (mediaPlayer.isPlaying && mediaPlayer.currentPosition <= max) curTime.value = mediaPlayer.currentPosition }
    )
  }

  fun unregisterTimerOnMediaPlayer() {
    rxDisposable.clear()
  }

  fun getNextSongs(songId: Int) {
    songRepo.loadRecommendedSongs(songId).toDisposal(rxDisposable, {
      nextSongList.value = it
    }, {
      doOnNetworkError(it)
    })
  }

  override fun onCleared() {
    super.onCleared()
    mediaPlayer.value?.release()
  }

  init {
    getSongDetail(songId)
    getNextSongs(songId)
  }
}