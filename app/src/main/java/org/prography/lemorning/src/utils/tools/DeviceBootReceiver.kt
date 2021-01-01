package org.prography.lemorning.src.utils.tools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.src.AlarmContextContent
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel
import org.prography.lemorning.src.views.AlarmSettingFragment

class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            /*val viewModel =
                ViewModelProvider(context as AlarmSettingFragment).get(MyAlarmsViewModel::class.java)
            val dbViewModel = ViewModelProvider(context).get(AlarmDBViewModel::class.java)

            if (dbViewModel.getAll().value != null) {
                dbViewModel.getAll().observe(context, androidx.lifecycle.Observer {
                    for (alarm in it) {
                        val contextContent = AlarmContextContent(context)
                        contextContent.getPendingIntent(alarm)?.let { pendingIntent ->
                            viewModel.setAlarmManager(
                                alarm,
                                pendingIntent, contextContent.getAlarmManager()
                            )
                        }
                    }
                })
            }*/

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context?.startForegroundService(Intent(context, AlarmService::class.java))
            } else {
                context?.startService(Intent(context, AlarmService::class.java))
            }
        }
    }
}

