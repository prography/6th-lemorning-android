package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.repositories.AlarmRepository
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val songRepo = SongRepository(application)
    private val alarmRepo = AlarmRepository(application)

    val songs: MutableLiveData<List<Song>> = MutableLiveData()
    val myAlarms: MutableLiveData<List<Alarm>> = MutableLiveData()

    fun fetchSongs() {
        songRepo.loadSongs().toDisposal(rxDisposable, {
            songs.value = it
        }, {
            doOnNetworkError(it)
        })
    }

    fun fetchMyAlarms() {
        alarmRepo.getAll().toDisposal(rxDisposable, {
            myAlarms.value = it
        }, {
            doOnNetworkError(it)
        })
    }

    init {
        fetchSongs()
        fetchMyAlarms()
    }
}