package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.AlarmContextContent
import org.prography.lemorning.src.viewmodel.AlarmViewModel

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, AlarmViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun getViewModel(): AlarmViewModel {
        return ViewModelProvider(this).get(AlarmViewModel::class.java)
    }

    override fun initView() {
        val contextContent = AlarmContextContent(this)
        val mediaPlayer = contextContent.getMediaPlayer(R.raw.ouu)
        viewmodel.playAlarm(mediaPlayer)
        alarm_off_button.setOnClickListener {
            viewmodel.stopAlarm(mediaPlayer)
            finish()
        }
    }
}