package org.prography.lemorning.src

import androidx.lifecycle.LiveData
import androidx.room.*
import org.prography.lemorning.src.model.Alarm

@Dao
interface AlarmDao{
    @Query("SELECT * FROM alarm ORDER BY id")
    fun getAll(): LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Alarm: Alarm)

    @Delete
    fun delete(Alarm: Alarm)
}