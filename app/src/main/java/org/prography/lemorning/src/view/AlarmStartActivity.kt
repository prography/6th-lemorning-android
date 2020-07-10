package org.prography.lemorning.src.view

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.viewmodel.PlaySongViewModel

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, PlaySongViewModel>() {

    override fun getViewModel(): PlaySongViewModel {
        val songNo = intent.getIntExtra("songNo", -1)

        return ViewModelProvider(this, PlaySongViewModelFactory(songNo)).get(PlaySongViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED    // 잠김 화면 위에 표시할 때 사용
            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD      // 순정 잠금 화면을 해제 할 때 사용
            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON         // 화면을 켜진 상태로 유지
            or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND)
        val audioAttributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener {  }
                .build()
            audioManager.requestAudioFocus(audioFocusRequest)
        }else{
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }

        alarm_note.text = intent.getStringExtra("alarmNote")

        NotificationManagerCompat.from(this).cancel(12345)

        viewmodel.mediaPlayer.observe(this, Observer {
            it?.setOnPreparedListener{mediaPlayer ->
                mediaPlayer.start()
            }
        })

        alarm_off_button.setOnClickListener {
            viewmodel.mediaPlayer.value?.stop()
            finish()
        }
    }

    class PlaySongViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlaySongViewModel(songNo) as T
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, AlarmService::class.java))
    }
}