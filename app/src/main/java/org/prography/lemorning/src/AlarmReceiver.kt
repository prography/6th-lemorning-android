package org.prography.lemorning.src

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.prography.lemorning.R
import org.prography.lemorning.src.view.AlarmStartActivity
import java.util.*

class AlarmReceiver: BroadcastReceiver() {
//    private var mWakeLock: PowerManager.WakeLock? = null

//    private val TAG = "Lemorning: AlarmWakeLock"  // 구분할 수 있는 문자열을 넣으면 된다.

    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            intent?.getIntExtra("id", -1)?.let { PendingIntent.getBroadcast(context, it, intent, PendingIntent.FLAG_CANCEL_CURRENT) }
        val calendar = Calendar.getInstance()

        val date = intent?.getLongExtra("date", 0)?.let { Date(it) }
        calendar.time = date
        calendar.add(Calendar.DATE, 1)


        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        intent?.getStringExtra("week").let {
            if(it?.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1) == '0'){
                return
            }
            if(it == null) return
        }
        // 휴대폰 Power 관련 코드
//        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager;  // PowerManager를 얻는다.
//
//        mWakeLock = pm.newWakeLock(
//            PowerManager.FULL_WAKE_LOCK or
//                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
//                    PowerManager.ON_AFTER_RELEASE, TAG
//        )
//
//        mWakeLock?.acquire();  // 휴대폰이 대기 상태로 가지 않도록 유지

        val alarmIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("songNo", intent?.getIntExtra("songNo", -1))
        }
//        alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(alarmIntent)
        }else{
            context.startService(alarmIntent)
        }

//        if(mWakeLock != null){
//            mWakeLock!!.release()
//            mWakeLock = null
//        }
    }


}