package org.prography.lemorning.src.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.viewmodel.AlarmViewModel

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, AlarmViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun getViewModel(): AlarmViewModel {
        return ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.ouu)
        viewmodel.playAlarm(mediaPlayer)
        alarm_off_button.setOnClickListener {
            viewmodel.stopAlarm(mediaPlayer)
            finish()
        }
    }
}