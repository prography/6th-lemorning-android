package org.prography.lemorning.src.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.DeviceBootReceiver
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.repository.AlarmRepository
import java.text.SimpleDateFormat
import java.util.*

class AlarmViewModel (application: Application): AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val alarms = repository.getAll()

    fun getAll(): LiveData<List<Alarm>> {
        return this.alarms
    }

    fun insert(Alarm: Alarm) {
        repository.insert(Alarm)
    }

    fun delete(Alarm: Alarm) {
        repository.delete(Alarm)
    }

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

        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
        calendar.set(java.util.Calendar.MINUTE, minute)
        calendar.set(java.util.Calendar.SECOND, 0)

        if (calendar.before(java.util.Calendar.getInstance())) calendar.add(java.util.Calendar.DATE, 1)

        val currentTime = calendar.time
        val timeText = SimpleDateFormat(
            "a hh : mm",
            Locale.getDefault()
        ).format(currentTime)
        Toast.makeText(getApplication(), timeText + "으로 알람이 설정되었습니다.", Toast.LENGTH_LONG).show()

        return Alarm(null, timeText, true, week, currentTime.toString())
    }

    fun setAlarmManager(context: Context, alarm: Alarm) {
        val calendar = java.util.Calendar.getInstance()

        val date = SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.getDefault()).parse(alarm.date)
        calendar.time = date

        val intent = Intent(getApplication(), AlarmReceiver::class.java)
        val pendingIntent = alarm.id?.let {
            PendingIntent.getBroadcast(
                getApplication(),
                it,
                intent,
                0
            )
        }
        val alarmManager = getApplication<Application>().getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY, pendingIntent
//        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    fun setBootAlarm(context: Context) {
        val packageManager = context.packageManager
        val receiver = ComponentName(context, DeviceBootReceiver::class.java)

        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun playAlarm(resId:Int): MediaPlayer {
        val mediaPlayer = MediaPlayer.create(getApplication(), resId)
        mediaPlayer.start()

        return mediaPlayer
    }

    fun stopAlarm(mediaPlayer: MediaPlayer){
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
    }
}