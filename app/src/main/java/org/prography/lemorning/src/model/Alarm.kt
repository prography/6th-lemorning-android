package org.prography.lemorning.src.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarm")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "on")
    var on: Boolean,
    @ColumnInfo(name = "week")
    var week: String,
    @ColumnInfo(name = "date")
    var date: Long,
    @ColumnInfo(name = "songNo")
    var songNo: Int
)