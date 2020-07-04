package org.prography.lemorning.src.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.repository.AlarmRepository
import org.prography.lemorning.src.view.AlarmSettingActivity

class AlarmDBViewModel(application: Application): AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val alarms = repository.getAll()

    fun getAll(): LiveData<List<Alarm>> {
        return this.alarms
    }

    fun insert(alarm: Alarm) {
        repository.insert(alarm)
    }

    fun delete(alarm: Alarm) {
        repository.delete(alarm)
    }

    fun getAlarm(id: Int): Alarm? {
        return repository.getAlarm(id)
    }

    fun cancelAlarm(alarm:Alarm){
        val alarmManager = getApplication<Application>().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(getApplication(), AlarmReceiver::class.java).apply {
            putExtra("songNo", alarm.songNo)
            putExtra("week", alarm.week)
            putExtra("id", alarm.id)
            putExtra("date", alarm.date)
        }
        val pendingIntent =
            alarm.id?.let {
                PendingIntent.getBroadcast(getApplication(),
                    it, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            }
        alarmManager.cancel(pendingIntent)
    }
}