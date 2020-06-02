package org.prography.lemorning.src.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_setting.*
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmSettingBinding
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAlarmSettingBinding>(
                this,
                R.layout.activity_alarm_setting
            )
        binding.vm = ViewModelProvider(this)
            .get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this
        val viewModel = binding.vm

        alarm_setting_button.setOnClickListener{
            val alarm = viewModel!!.setAlarm(alarm_setting_time_picker, alarm_setting_week)
            if(alarm.week == "0000000"){
                Toast.makeText(
                    this,
                    "요일을 선택해주세요",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                viewModel.insert(alarm)
                viewModel.getAll().observe(this, Observer {
                    for(a in it){
                        if(alarm.date == a.date) viewModel.setAlarmManager(this, a)
                    }
                })
                viewModel.setBootAlarm(this)
                finish()
            }
        }
    }
}