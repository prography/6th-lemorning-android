package org.prography.lemorning.src.viewmodels

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.core.view.get
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.views.adapters.MyAlarmsAdapter
import java.text.SimpleDateFormat
import java.util.*

class MyAlarmsViewModel(application: Application) : BaseViewModel(application) {

    var alarmNote = ""
    val myAlarmsAdapter = MyAlarmsAdapter(this)

    fun setAlarm(timePicker: TimePicker, linearLayout: LinearLayout, songNo: Int, imgUrl: String): Alarm {
        val hour = timePicker.hour
        val minute = timePicker.minute

        var week = ""

        for (i in 0..6) {
            if ((linearLayout[i] as CheckBox).isChecked) {
                week += "1"
            } else week += "0"
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1)

        val currentTime = calendar.time
        val timeText = SimpleDateFormat(
            "a hh : mm",
            Locale.getDefault()
        ).format(currentTime)

        return Alarm(0, timeText, true, week, currentTime.time, songNo, imgUrl, alarmNote)
    }

    fun setAlarmManager(alarm: Alarm, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
        val calendar = Calendar.getInstance()

        val date = Date(alarm.date)
        calendar.time = date

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun setBootAlarm(packageManager: PackageManager, receiver: ComponentName) {
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun updateOn(alarm: Alarm, on:Boolean){
        alarm.on = on
        repository.update(alarm)
    }

    fun cancelAlarm(alarm: Alarm){
        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(getApplication(), AlarmReceiver::class.java).apply {
            putExtra("id", alarm.id)
            putExtra("songNo", alarm.songNo)
            putExtra("week", alarm.week)
            putExtra("date", alarm.date)
            putExtra("alarmNote", alarm.alarmNote)
        }
        val pendingIntent = PendingIntent.getBroadcast(getApplication(), alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.cancel(pendingIntent)
    }
}