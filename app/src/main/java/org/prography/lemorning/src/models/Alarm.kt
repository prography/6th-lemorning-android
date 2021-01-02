package org.prography.lemorning.src.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Alarm")
data class Alarm (
  @PrimaryKey(autoGenerate = true)
  @SerializedName("id")
  val id: Int = 0,
  @SerializedName("time") val time: String,
  @SerializedName("on") val on: Boolean,
  @SerializedName("week") val week: String,
  @SerializedName("date") val date: Long,
  @SerializedName("songNo") val songNo: Int,
  @SerializedName("imgUrl") val imgUrl: String,
  @SerializedName("alarmNote") val alarmNote: String
)