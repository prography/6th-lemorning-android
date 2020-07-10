package org.prography.lemorning.src.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.adapters.PlayRecommendAdapter
import org.prography.lemorning.src.apis.PlaySongApiService
import org.prography.lemorning.src.models.PlaySong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class PlaySongViewModel(var songNo: Int) : BaseViewModel() {

    val playRecommendAdapter = PlayRecommendAdapter(viewModel = this)

    var playSong: LiveData<PlaySong> = getSong(songNo)
    var nextSongList: LiveData<ArrayList<PlaySong?>> = getNextSongs()

    var mediaPlayer: MutableLiveData<MediaPlayer?> = MutableLiveData()

    fun getSong(songNo: Int) : LiveData<PlaySong> {
        var result: MutableLiveData<PlaySong> = MutableLiveData()
        ApplicationClass.retrofit.create(PlaySongApiService::class.java).getPlaySong(songNo).enqueue(object : Callback<PlaySong> {
            override fun onFailure(call: Call<PlaySong>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PlaySong>, response: Response<PlaySong>) {
                var playSong : PlaySong? = response.body()
                if (playSong == null) {
                    return
                }
                result.value = playSong
                mediaPlayer.value = MediaPlayer().apply {
                    setDataSource(playSong.musicUrl)
                    setScreenOnWhilePlaying(true)
                    isLooping = true
                    prepareAsync() // might take long! (for buffering, etc)
                }
            }

        })
        return result
    }

    fun makeCurrentTimeRx (mediaPlayer: MediaPlayer) : Flowable<Int> {
        return Flowable.just(mediaPlayer.currentPosition)
            .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getNextSongs() : LiveData<ArrayList<PlaySong?>> {
        var result: MutableLiveData<ArrayList<PlaySong?>> = MutableLiveData()
        ApplicationClass.retrofit.create(PlaySongApiService::class.java).getNextSongs().enqueue(object : Callback<ArrayList<PlaySong?>> {
            override fun onFailure(call: Call<ArrayList<PlaySong?>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<PlaySong?>>, response: Response<ArrayList<PlaySong?>>) {
                response.body()?.let { result.value = it }
            }

        })
        return result
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.value?.release()
    }
}