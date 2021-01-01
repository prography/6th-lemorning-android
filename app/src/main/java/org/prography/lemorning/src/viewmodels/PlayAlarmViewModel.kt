package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal


class PlayAlarmViewModel(val songId: Int, application: Application) : BaseViewModel(application) {
    private val songRepo = SongRepository(application)

    val songDetail: MutableLiveData<SongDetail> = MutableLiveData()
    val mediaPlayer: MutableLiveData<MediaPlayer?> = MutableLiveData()

    private fun fetchSongDetail(songId: Int) {
        songRepo.loadSongDetail(songId).toDisposal(rxDisposable, {
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

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.value?.release()
    }

    init {
        fetchSongDetail(songId)
    }
}