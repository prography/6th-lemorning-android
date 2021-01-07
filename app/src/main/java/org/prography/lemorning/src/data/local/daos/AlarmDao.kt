package org.prography.lemorning.src.data.local.daos

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.src.models.Alarm

@Dao
interface AlarmDao {
  @Query("SELECT * FROM Alarm ORDER BY id")
  fun getAllAlarms(): Single<List<Alarm>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAlarm(alarm: Alarm): Completable

  @Query("DELETE FROM Alarm WHERE id = :id")
  fun deleteAlarm(id: Int): Completable

  @Query("SELECT * FROM Alarm WHERE id = :id")
  fun getAlarm(id: Int): Maybe<Alarm>

  @Query("UPDATE Alarm SET `on` = :on WHERE id = :id")
  fun updateAlarmStatus(id: Int, on: Boolean): Completable
}