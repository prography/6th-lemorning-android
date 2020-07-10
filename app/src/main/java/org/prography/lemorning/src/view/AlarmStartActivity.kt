package org.prography.lemorning.src.view

import android.content.Intent
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
import org.prography.lemorning.src.viewmodel.PlayAlarmViewModel

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, PlayAlarmViewModel>() {

    override fun getViewModel(): PlayAlarmViewModel {
        val songNo = intent.getIntExtra("songNo", -1)

        return ViewModelProvider(this, PlayAlarmViewModelFactory(songNo)).get(PlayAlarmViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED    // 잠김 화면 위에 표시할 때 사용
            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD      // 순정 잠금 화면을 해제 할 때 사용
            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON         // 화면을 켜진 상태로 유지
            or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

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

    class PlayAlarmViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayAlarmViewModel(songNo) as T
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, AlarmService::class.java))
    }
}