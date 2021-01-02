package org.prography.lemorning.src.repositories

import android.app.Application
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.src.data.local.LemorningDatabase
import org.prography.lemorning.src.data.local.daos.AlarmDao
import org.prography.lemorning.src.models.Alarm

class AlarmRepository(application: Application) : BaseRepository(application) {
  private val alarmDao: AlarmDao = LemorningDatabase.getInstance(application).alarmDao()

  fun getAllMyAlarams(): Single<List<Alarm>> {
    return alarmDao.getAllAlarms()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun insert(alarm: Alarm): Completable {
    return alarmDao.insertAlarm(alarm)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun delete(alarm: Alarm): Completable {
    return alarmDao.deleteAlarm(alarm.id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun getAlarm(id: Int): Maybe<Alarm> {
    return alarmDao.getAlarm(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  fun updateAlarmStatus(alarmId: Int, on: Boolean): Completable {
    return alarmDao.updateAlarmStatus(alarmId, on)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }
}