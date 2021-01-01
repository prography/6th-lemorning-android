package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.repositories.AlarmRepository
import org.prography.lemorning.src.views.adapters.AlarmSettingRecyclerAdapter

class AlarmSettingViewModel(application: Application) : BaseViewModel(application) {
    val alarmRepo = AlarmRepository(application)
    val newAlarmNote = MutableLiveData("")

    val circularSongListAdapter = AlarmSettingRecyclerAdapter(this)

    fun insertMyNewAlarm(alarm: Alarm) {

    }
}