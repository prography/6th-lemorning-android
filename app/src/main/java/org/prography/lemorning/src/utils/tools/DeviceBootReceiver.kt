package org.prography.lemorning.src.utils.tools

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.data.local.LemorningDatabase
import java.util.*

class DeviceBootReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
      if (context == null) return
      val alarmDao = LemorningDatabase.getInstance(context).alarmDao()
      CompositeDisposable().add(
        alarmDao.getAllAlarms()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            it.forEach { alarm ->
              val alarmIntent = Intent(context.applicationContext, AlarmReceiver::class.java).apply { /*putExtra("alarm", alarm)*/ }
              val pendingIntent = PendingIntent.getBroadcast(
                context.applicationContext,
                alarm.id,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
              )
              val calendar = Calendar.getInstance().apply { time = Date(alarm.date) }
              alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
          }, {
            it.printStackTrace()
          })
      )
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(Intent(context.applicationContext, AlarmService::class.java))
      } else {
        context.startService(Intent(context.applicationContext, AlarmService::class.java))
      }
    }
  }
}

