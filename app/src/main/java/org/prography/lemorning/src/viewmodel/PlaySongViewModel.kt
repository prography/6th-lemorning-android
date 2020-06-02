package org.prography.lemorning.src.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.adapters.PlayRecommendAdapter
import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.repository.networks.PlaySongApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaySongViewModel(var songNo: Int) : BaseViewModel() {

    val playRecommendAdapter = PlayRecommendAdapter(viewModel = this)

    var playSong: LiveData<PlaySong> = getSong(songNo)

    lateinit var mediaPlayer: MediaPlayer

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
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(playSong.musicUrl)
                    prepare() // might take long! (for buffering, etc)
                }
            }

        })
        return result
    }
}