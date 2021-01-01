package org.prography.lemorning.src.data.local.daos

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.src.models.Alarm

@Dao
interface AlarmDao{
    @Query("SELECT * FROM alarm ORDER BY id")
    fun getAll(): Single<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm): Completable

    @Delete
    fun delete(alarm: Alarm): Completable

    @Query("SELECT * FROM alarm WHERE id = :id")
    fun getAlarm(id : Int): Maybe<Alarm>
  
    @Update
    fun update(alarm: Alarm): Completable
}