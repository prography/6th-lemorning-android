package org.prography.lemorning.src.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.viewmodel.AlarmViewModel

class AlarmStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAlarmStartBinding>(this, R.layout.activity_alarm_start)
        binding.vm = ViewModelProvider(this).get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this
        val viewModel = binding.vm

        if(viewModel != null){
            val mediaPlayer = viewModel.playAlarm(R.raw.ouu)
            alarm_off_button.setOnClickListener {
                viewModel.stopAlarm(mediaPlayer)
                startActivity(Intent(this, AlarmActivity::class.java))
                finish()
            }
        }
    }
}