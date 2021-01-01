package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmSettingBinding
import org.prography.lemorning.src.AlarmContextContent
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.views.adapters.AlarmSettingRecyclerAdapter
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel


class AlarmSettingActivity :
    BaseActivity<ActivityAlarmSettingBinding, MyAlarmsViewModel>(R.layout.activity_alarm_setting) {

    override fun getViewModel(): MyAlarmsViewModel {
        return ViewModelProvider(this)
            .get(MyAlarmsViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {


        binding.alarmSettingButton.setOnClickListener {
            val contextContent = AlarmContextContent(applicationContext)

            val alarmSongNo =
                (binding.alarmSettingRecycler.adapter as AlarmSettingRecyclerAdapter).selectItemSongNo
            val alarmImgUrl =
                (binding.alarmSettingRecycler.adapter as AlarmSettingRecyclerAdapter).selectItemUrl

            if (alarmSongNo == -1) {
                showToast("음악을 선택해주세요")
                return@setOnClickListener
            }

            val alarm = vm.setAlarm(
                binding.alarmSettingTimePicker,
                binding.alarmSettingWeek,
                alarmSongNo,
                alarmImgUrl
            )

            if (alarm.week == "0000000") {
                showToast("요일을 선택해주세요")
            } else {
                dbViewModel.insert(alarm)
                dbViewModel.getAll().observe(this, Observer {
                    for (a in it) {
                        if (alarm.date == a.date) {
                            contextContent.getPendingIntent(a)?.let { pendingIntent ->
                                vm.setAlarmManager(
                                    a,
                                    pendingIntent,
                                    contextContent.getAlarmManager()
                                )
                            }
                            vm.setBootAlarm(
                                contextContent.getPackageManager(),
                                contextContent.getComponentName()
                            )
                        }
                    }
                })
                showToast(alarm.time + "으로 알람이 설정되었습니다")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(Intent(this, AlarmService::class.java))
                } else {
                    startService(Intent(this, AlarmService::class.java))
                }

                finish()
            }
        }
    }
}