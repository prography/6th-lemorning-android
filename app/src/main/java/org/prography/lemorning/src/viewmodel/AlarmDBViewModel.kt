package org.prography.lemorning.src.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.repository.AlarmRepository

class AlarmDBViewModel(application: Application): AndroidViewModel(application) {
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
}