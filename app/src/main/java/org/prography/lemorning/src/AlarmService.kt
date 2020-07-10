package org.prography.lemorning.src

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.prography.lemorning.R
import org.prography.lemorning.src.view.AlarmStartActivity

class AlarmService: Service() {

    private val CHANNEL_ID = "Lemorning"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmIntent = Intent(this, AlarmStartActivity::class.java).apply {
            putExtra("songNo", intent?.getIntExtra("songNo", -1))
        }
        val fullScreenIntent = PendingIntent.getActivity(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_HIGH,
            false, "Lemorning", "App Notification channel")
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lemorning)
            .setContentTitle("Lemorning 알람")
            .setContentText("알람: Note")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setFullScreenIntent(fullScreenIntent, true)

//        NotificationManagerCompat.from(this).notify(12345, notificationBuilder.build())
        startForeground(12345, notificationBuilder.build())
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean,
                                          name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(name, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}