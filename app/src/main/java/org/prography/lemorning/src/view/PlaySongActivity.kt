package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityPlaySongBinding
import org.prography.lemorning.src.viewmodel.PlaySongViewModel
import org.prography.lemorning.utils.BindingAdapters

class PlaySongActivity(override val layoutId: Int = R.layout.activity_play_song)
    : BaseActivity<ActivityPlaySongBinding, PlaySongViewModel>() {

    override fun getViewModel(): PlaySongViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, PlaySongViewModelFactory(songNo)).get(PlaySongViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {

        /* 추천 RecyclerView */
        binding.rvRecommendPlaySong.adapter = viewmodel.playRecommendAdapter

        binding.visualizerPlaySong.setColor(getColor(R.color.colorPrimary))

        /* Alarm Play & Stop */
        binding.ivPlayPlaySong.setOnClickListener {
            viewmodel.mediaPlayer.let { mediaPlayer ->
                mediaPlayer.value?.let {
                    if (it.isPlaying) {
                        it.pause()
                    } else {
                        it.start()
                    }
                }
            }
        }
        binding.sdSeekbarPlaySong.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) viewmodel.mediaPlayer.value?.seekTo(value.toInt())
        }

        viewmodel.mediaPlayer.observe(this,  Observer {
            run {
                /* Audio Visualizer */
                it?.setOnPreparedListener {
                    binding.visualizerPlaySong.setPlayer(it.audioSessionId)
                    binding.sdSeekbarPlaySong.valueTo = it.duration.toFloat()
                    binding.sdSeekbarPlaySong.valueFrom = it.currentPosition.toFloat()
                    binding.tvStartPlaySong.text = it.currentPosition.toString()
                    binding.tvEndPlaySong.text = it.duration.toString()
                    it.start()
                }
            }
        })
        //viewmodel.mediaPlayer.value?.listener
        binding.ivClosePlaySong.setOnClickListener { finish() }

    }


    override fun onDestroy() {
        viewmodel.mediaPlayer.value?.release()
        super.onDestroy()
    }

    class PlaySongViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlaySongViewModel(songNo) as T
    }
}