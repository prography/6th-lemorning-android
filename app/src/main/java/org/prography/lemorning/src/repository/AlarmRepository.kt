package org.prography.lemorning.src.repository

import android.app.Application
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.repository.db.AlarmDao
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.repository.db.AlarmDatabase
import java.lang.Exception

class AlarmRepository(application: Application){
    private val alarmDatabase = AlarmDatabase.getInstance(
        application
    )!!
    private val alarmDao: AlarmDao = alarmDatabase.alarmDao()
    private val alarms: LiveData<List<Alarm>> = alarmDao.getAll()

    fun getAll(): LiveData<List<Alarm>> {
        return alarms
    }

    fun insert(Alarm: Alarm){
        try{
            val thread = Thread(Runnable {
                alarmDao.insert(Alarm)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun delete(Alarm: Alarm){
        try{
            val thread = Thread(Runnable {
                alarmDao.delete(Alarm)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}