package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentAlarmSettingBinding
import org.prography.lemorning.src.AlarmContextContent
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.viewmodels.AlarmSettingViewModel
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.views.adapters.AlarmSettingRecyclerAdapter


class AlarmSettingFragment :
    BaseFragment<FragmentAlarmSettingBinding, AlarmSettingViewModel, MainViewModel>(R.layout.fragment_alarm_setting) {

    override fun getViewModel(): AlarmSettingViewModel = ViewModelProvider(this).get(AlarmSettingViewModel::class.java)

    override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.alarmSettingRecycler.adapter = vm.circularSongListAdapter

        pvm.songs.observe(this) { /*vm.circularSongListAdapter.setItem(it)*/ }

        // TODO: 로직 수정
        /*binding.alarmSettingButton.setOnClickListener {
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
                vm.insertMyNewAlarm(Alarm(
                    time = ,
                    on = ,
                    week = ,
                    date = ,
                    songNo = ,
                    imgUrl = ,
                    alarmNote = ""
                ))
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
                    activity?.startForegroundService(Intent(activity, AlarmService::class.java))
                } else {
                    activity?.startService(Intent(activity, AlarmService::class.java))
                }
                findNavController().navigate(R.id.fg_alarm)
            }
        }*/
    }
}