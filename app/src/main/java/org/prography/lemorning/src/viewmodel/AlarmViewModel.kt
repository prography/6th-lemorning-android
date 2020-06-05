package org.prography.lemorning.src.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.core.view.get
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.model.Alarm
import java.text.SimpleDateFormat
import java.util.*

class AlarmViewModel: BaseViewModel() {

    fun setAlarm(timePicker: TimePicker, linearLayout: LinearLayout): Alarm {
        var hour = 0
        var minute = 0

        hour = timePicker.hour
        minute = timePicker.minute

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

        return Alarm(null, timeText, true, week, currentTime.time, 5)
    }

    fun setAlarmManager(alarm: Alarm, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
        val calendar = Calendar.getInstance()

        val date = Date(alarm.date)
        calendar.time = date

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    fun setBootAlarm(packageManager: PackageManager, receiver: ComponentName) {
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun currentTime(): String {
        val cur = SimpleDateFormat("hh:mm:ss", Locale.US).format(Date(System.currentTimeMillis()))
        return if(cur < "19:00:00" && cur >= "11:00:00") "주간"
        else if(cur < "11:00:00" && cur >= "06:00:00") "아침"
        else "야간"
    }
}