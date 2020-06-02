package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm.*
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmBinding
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAlarmBinding>(
                this,
                R.layout.activity_alarm
            )
        binding.alarmViewModel = ViewModelProvider(this)
            .get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this

        alarm_image.setOnClickListener {
            val intent = Intent(
                this,
                AlarmSettingActivity::class.java
            )
            startActivity(intent)
        }
    }
}