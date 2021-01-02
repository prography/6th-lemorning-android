package org.prography.lemorning.src.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "alarm")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "on")
    val on: Boolean,
    @ColumnInfo(name = "week")
    var week: String,
    @ColumnInfo(name = "date")
    var date: Long,
    @ColumnInfo(name = "songNo")
    var songNo: Int,
    @ColumnInfo(name = "imgUrl")
    var imgUrl: String,
    @ColumnInfo(name = "alarmNote")
    var alarmNote: String
): Parcelable