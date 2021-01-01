package org.prography.lemorning.src.views

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.BuildConfig
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityPlaySongBinding
import org.prography.lemorning.src.viewmodels.SongDetailViewModel
import org.prography.lemorning.src.utils.Converters
import org.prography.lemorning.src.utils.components.SimpleMessageDialog

class SongDetailActivity
    : BaseActivity<ActivityPlaySongBinding, SongDetailViewModel>(R.layout.activity_play_song) {

    override fun getViewModel(): SongDetailViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, SongDetailViewModelFactory(songNo, application)).get(SongDetailViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.rvRecommendPlaySong.adapter = vm.playRecommendAdapter
        vm.nextSongList.observe(this) { vm.playRecommendAdapter.setItem(it) }

        binding.visualizerPlaySong.setColor(getColor(R.color.colorPrimary))

        /* Alarm Play & Stop */
        binding.ivPlayPlaySong.setOnClickListener { vm.mediaPlayer.let { it.value?.let {
            if (it.isPlaying) {
                it.pause()
                binding.ivPlayPlaySong.setImageResource(R.drawable.ic_play_big)
            } else {
                it.start()
                binding.ivPlayPlaySong.setImageResource(R.drawable.ic_pause)
            }
        } } }
        binding.sdSeekbarPlaySong.addOnChangeListener { _, value, fromUser -> if (fromUser) vm.mediaPlayer.value?.seekTo(value.toInt()) }

        vm.mediaPlayer.observe(this,  Observer {
            /* Audio Visualizer */
            it?.setOnPreparedListener {
                //binding.visualizerPlaySong.setPlayer(it.audioSessionId)
                binding.sdSeekbarPlaySong.valueTo = it.duration.toFloat()
                binding.sdSeekbarPlaySong.valueFrom = it.currentPosition.toFloat()
                binding.tvEndPlaySong.text = Converters.mediaPositionToRealTimeTxt(it.duration)
                it.start()
                vm.registerTimerOnMediaPlayer(it)
            }
        })

        /* Set On Click Listener */
        binding.ivClosePlaySong.setOnClickListener { finish() }
        binding.ivMorePlaySong.setOnClickListener { SimpleMessageDialog(
            context = this,
            message = getString(R.string.coming_soon)
        ).show() }
        binding.ivLikePlaySong.setOnClickListener { SimpleMessageDialog(
            context = this,
            message = getString(R.string.coming_soon)
        ).show() }

    }

    override fun onPause() {
        super.onPause()
        if (!BuildConfig.DEBUG) {
            vm.mediaPlayer.value?.let { if (it.isPlaying) it.pause() }
        }
    }


    override fun onDestroy() {
        vm.mediaPlayer.value?.release()
        vm.mediaPlayer.value = null
        super.onDestroy()
    }

    class SongDetailViewModelFactory(val songNo: Int, val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = SongDetailViewModel(songNo, application) as T
    }
}