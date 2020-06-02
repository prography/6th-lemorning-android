package org.prography.lemorning.src.view

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityPlaySongBinding
import org.prography.lemorning.src.viewmodel.PlaySongViewModel

class PlaySongActivity(override val layoutId: Int = R.layout.activity_play_song)
    : BaseActivity<ActivityPlaySongBinding, PlaySongViewModel>() {

    override fun getViewModel(): PlaySongViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, PlaySongViewModelFactory(songNo)).get(PlaySongViewModel::class.java)
    }

    override fun initView() {

        /* 추천 RecyclerView */
        binding.rvRecommendPlaySong.adapter = viewmodel.playRecommendAdapter



        /* Alarm Play & Stop */
        binding.ivPlayPlaySong.setOnClickListener {
            viewmodel.mediaPlayer.let { mediaPlayer ->
                run {
                    when {
                        mediaPlayer.isPlaying -> mediaPlayer.pause()
                        else -> mediaPlayer.start()
                    }
                }
            }
        }

        /* Data Observing */
        viewmodel.prepareVisualizer.observe(this, Observer {t ->
            var event = t.get()
            if (event != null) {
                /* Audio Visualizer */
                //binding.visualizerPlaySong.setPlayer(viewmodel.mediaPlayer.audioSessionId)
            }
        })
    }

    class PlaySongViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlaySongViewModel(songNo) as T
    }
}