package org.prography.lemorning.src.view

import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_setting.*
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmSettingBinding
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmSettingActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAlarmSettingBinding>(this, R.layout.activity_alarm_setting)
        binding.vm = ViewModelProvider(this).get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this
        val viewModel = binding.vm

        alarm_setting_button.setOnClickListener{
            val alarm = viewModel!!.setAlarm(alarm_setting_time_picker, Calendar.getInstance(), alarm_setting_week)
            viewModel.insert(alarm)
            finish()
        }
    }
}
