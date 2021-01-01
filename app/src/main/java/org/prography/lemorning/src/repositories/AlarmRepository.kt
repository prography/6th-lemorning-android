package org.prography.lemorning.src.repositories

import android.app.Application
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.data.local.daos.AlarmDao
import org.prography.lemorning.src.data.local.LemorningDatabase

class AlarmRepository(application: Application): BaseRepository(application) {
    private val alarmDao: AlarmDao = LemorningDatabase.getInstance(application).alarmDao()

    fun getAll(): Single<List<Alarm>> {
        return alarmDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insert(alarm: Alarm): Completable {
        return alarmDao.insert(alarm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(alarm: Alarm): Completable {
        return alarmDao.delete(alarm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAlarm(id: Int): Maybe<Alarm> {
        return alarmDao.getAlarm(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(alarm: Alarm): Completable {
        return alarmDao.update(alarm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}