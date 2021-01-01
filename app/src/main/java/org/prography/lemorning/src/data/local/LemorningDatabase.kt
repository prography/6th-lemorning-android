package org.prography.lemorning.src.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.prography.lemorning.src.data.local.daos.AlarmDao
import org.prography.lemorning.src.models.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class LemorningDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object{

        @Volatile
        private var INSTANCE: LemorningDatabase? = null

        fun getInstance(context: Context): LemorningDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                LemorningDatabase::class.java,
                "lemorning.db"
            ).build().also { INSTANCE = it }
        }
    }
}