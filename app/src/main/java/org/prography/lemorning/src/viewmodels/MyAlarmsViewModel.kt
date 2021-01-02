package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.repositories.AlarmRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal
import org.prography.lemorning.src.utils.objects.SingleEvent
import org.prography.lemorning.src.views.adapters.MyAlarmsAdapter
import java.text.SimpleDateFormat
import java.util.*

class MyAlarmsViewModel(application: Application) : BaseViewModel(application) {
    private val alarmRepo = AlarmRepository(application)

    var alarmNote = ""
    val myAlarmsAdapter = MyAlarmsAdapter(this)
    val isEditing = MutableLiveData(false)
    val selectedAlarms: MutableLiveData<Set<Alarm>> = MutableLiveData(setOf())
    val updateAlarmEvent: MutableLiveData<SingleEvent<Pair<Alarm, Boolean>>> = MutableLiveData()

    fun setAlarm(
        timePicker: TimePicker,
        linearLayout: LinearLayout,
        songNo: Int,
        imgUrl: String
    ): Alarm {
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

    fun setBootAlarm(packageManager: PackageManager, receiver: ComponentName) {
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun updateAlarm(alarm: Alarm, on: Boolean) {
        alarmRepo.updateAlarmStatus(alarm.id, on)
            .toDisposal(rxDisposable, {
                updateAlarmEvent.value = SingleEvent(Pair(alarm, on))
            }, {
                doOnNetworkError(it)
            })
    }
}