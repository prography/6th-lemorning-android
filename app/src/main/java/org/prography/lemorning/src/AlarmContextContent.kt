package org.prography.lemorning.src

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import org.prography.lemorning.src.model.Alarm

class AlarmContextContent(private val context: Context){

    fun getPendingIntent(alarm: Alarm): PendingIntent?{
        val intent = Intent(context, AlarmReceiver::class.java)

        return alarm.id?.let {
            PendingIntent.getBroadcast(
                context,
                it,
                intent,
                0
            )
        }
    }

    fun getAlarmManager():AlarmManager{
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun getPackageManager():PackageManager{
        return context.packageManager
    }

    fun getComponentName():ComponentName{
        return ComponentName(context, DeviceBootReceiver::class.java)
    }
}