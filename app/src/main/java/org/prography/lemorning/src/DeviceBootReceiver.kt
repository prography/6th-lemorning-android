package org.prography.lemorning.src

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.src.view.AlarmSettingActivity
import org.prography.lemorning.src.viewmodel.AlarmViewModel
import java.util.*

class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Objects.equals(intent?.action, "android.intent.action.BOOT_COMPLETED")) {
            val viewModel =
                ViewModelProvider(context as AlarmSettingActivity).get(AlarmViewModel::class.java)

            if (viewModel.getAll().value != null) {
                viewModel.getAll().observe(context, androidx.lifecycle.Observer {
                    for(alarm in it){
                        viewModel.setAlarmManager(context, alarm)
                    }
                })
            }
        }
    }
}

