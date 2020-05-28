package org.prography.lemorning.src.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.repository.AlarmRepository

class AlarmViewModel (application: Application): AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val alarms = repository.getAll()

    fun getAll():LiveData<List<Alarm>>{
        return this.alarms
    }

    fun insert(Alarm: Alarm){
        repository.insert(Alarm)
    }

    fun delete(Alarm: Alarm){
        repository.delete(Alarm)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setAlarm(timePicker: TimePicker, calendar: Calendar, linearLayout: LinearLayout):Alarm{

        val hour = timePicker.hour
        val minute = timePicker.minute

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        var week = ""

        for(i in 0..6){
            if((linearLayout[i] as CheckBox).isChecked) week += "1"
            else week += "0"
        }

        return Alarm(null, "$hour : $minute", true, week)
    }
}