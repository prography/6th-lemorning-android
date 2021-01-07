package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.models.Card
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.repositories.AlarmRepository
import org.prography.lemorning.src.repositories.PaymentRepository
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal

class MainViewModel(application: Application) : BaseViewModel(application) {
  private val songRepo = SongRepository(application)
  private val alarmRepo = AlarmRepository(application)
  private val payRepo = PaymentRepository(application)

  val songs: MutableLiveData<List<Song>> = MutableLiveData()
  val mySongs: MutableLiveData<List<SongDetail>> = MutableLiveData()
  val myAlarms: MutableLiveData<List<Alarm>> = MutableLiveData()
  val myCards: MutableLiveData<List<Card>> = MutableLiveData()

  fun fetchSongs() {
    songRepo.loadSongs().toDisposal(rxDisposable, {
        songs.value = it
      mySongs.value = it.map { SongDetail(it.id, it.title, it.category, it.thumbnail, "", it.tags) }
    }, {
        doOnNetworkError(it)
    })
  }

  fun fetchMyAlarms() {
    alarmRepo.getAllMyAlarams().toDisposal(rxDisposable, {
        myAlarms.value = it
    }, {
        doOnNetworkError(it)
    })
  }

  fun fetchMyPayments() {
    payRepo.loadCreditCards().toDisposal(rxDisposable, {
        myCards.value = it
    }, {
        doOnNetworkError(it)
    })
  }

  init {
    fetchSongs()
    fetchMyAlarms()
    fetchMyPayments()
  }
}