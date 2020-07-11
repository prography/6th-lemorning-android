package org.prography.lemorning.src

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.R
import org.prography.lemorning.src.view.AlarmStartActivity
import org.prography.lemorning.utils.FirebaseUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AlarmService: Service() {

    private val CHANNEL_ID = "Lemorning"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseUtils.initRemoteConfig({
            ApplicationClass.BASE_URL = it.getString(FirebaseUtils.BASE_URL_KEY)
        }, {
            ApplicationClass.BASE_URL = it.getString(FirebaseUtils.BASE_URL_KEY)
            ApplicationClass.retrofit = Retrofit.Builder()
                .baseUrl(ApplicationClass.BASE_URL)
                .client(ApplicationClass.client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        })
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmNote = intent?.getStringExtra("alarmNote")
        val alarmIntent = Intent(this.applicationContext, AlarmStartActivity::class.java).apply {
            putExtra("songNo", intent?.getIntExtra("songNo", -1))
            putExtra("alarmNote", alarmNote)
            this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val fullScreenIntent = PendingIntent.getActivity(this.applicationContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_HIGH,
            false, "Lemorning", "App Notification channel")

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lemorning)
            .setContentTitle("Lemorning 알람")
            .setContentText(if(alarmNote?.isNotEmpty()!!) alarmNote else "우와오아와오아오아아오와")
            .setFullScreenIntent(fullScreenIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(12345, notificationBuilder.build())

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