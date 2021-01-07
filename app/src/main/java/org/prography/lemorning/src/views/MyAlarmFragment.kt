package org.prography.lemorning.src.views

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentMyAlarmsBinding
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel
import java.util.*

class MyAlarmFragment : BaseFragment<FragmentMyAlarmsBinding, MyAlarmsViewModel, MainViewModel>(R.layout.fragment_my_alarms) {

  override fun getViewModel(): MyAlarmsViewModel = ViewModelProvider(this).get(MyAlarmsViewModel::class.java)

  override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    binding.rvMyAlarms.adapter = vm.myAlarmsAdapter
    binding.alarmEditButton.setOnClickListener { vm.isEditing.value = !vm.isEditing.value!! }
    binding.alarmAdd.setOnClickListener {
      findNavController().navigate(R.id.fg_alarm_setting)
    }

    vm.isEditing.observe(this) { vm.myAlarmsAdapter.setIsEditing(it) }
    pvm.myAlarms.observe(this) { vm.myAlarmsAdapter.setItem(it) }
    vm.updateAlarmEvent.observe(this) {
      pvm.fetchMyAlarms()
      it.get()?.let { alarm ->
        val alarmManager: AlarmManager = requireContext().applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext().applicationContext, AlarmReceiver::class.java).apply {
          /*putExtra("alarm", alarm.first)*/
        }
        val pendingIntent = PendingIntent.getBroadcast(requireContext().applicationContext, alarm.first.id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        if (alarm.second) {
          val calendar = Calendar.getInstance().apply { time = Date(alarm.first.date) }
          alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
          alarmManager.cancel(pendingIntent)
        }
      }
    }
  }
}