package org.prography.lemorning.src

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.orhanobut.logger.Logger
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.prography.lemorning.ApplicationClass.Companion.retrofit
import org.prography.lemorning.R
import org.prography.lemorning.src.data.remote.XAccessTokenInterceptor
import org.prography.lemorning.src.data.remote.apis.SongApi
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.utils.Constants.BASE_URL
import org.prography.lemorning.src.views.AlarmStartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AlarmService : Service() {

    private val CHANNEL_ID = "Lemorning"
    private lateinit var mediaPlayer: MediaPlayer

    private val rxDisposable = CompositeDisposable()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(
            this, NotificationManagerCompat.IMPORTANCE_LOW,
            false, "Lemorning", "App Notification channel"
        )
        createNotificationChannel(
            this, NotificationManagerCompat.IMPORTANCE_HIGH,
            false, "Lemorning Alarm", "App Notification channel"
        )

        makeFirstAlarmNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!rxDisposable.isDisposed) rxDisposable.dispose()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "AlarmStart") {
            setRetrofit()
            val alarmNote = intent.getStringExtra("alarmNote")

            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

            if (powerManager.isInteractive) {
                val alarmIntent = Intent(this.applicationContext, AlarmService::class.java).apply {
                    this.action = "AlarmStop"
                }
                makeAlarmNotification(alarmIntent, alarmNote!!)
                playAlarm(intent.getIntExtra("songNo", -1))
            } else {
                val alarmIntent =
                    Intent(this.applicationContext, AlarmStartActivity::class.java).apply {
                        putExtra("songNo", intent.getIntExtra("songNo", -1))
                        putExtra("alarmNote", alarmNote)
                        this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }

                makeAlarmNotification(alarmIntent, alarmNote!!)
            }
        } else if (intent?.action == "AlarmStop") {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(
        context: Context, importance: Int, showBadge: Boolean,
        name: String, description: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(name, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            if (message.startsWith("{") && message.endsWith("}")) {
                Logger.t("OKHTTP").json(message)
            } else {
                Log.i("OKHTTP", message)
            }
        }.apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .build()
    }

    fun makeFirstAlarmNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lemorning)
            .setContentTitle("Lemorning 알람")
            .setContentText("알람이 설정되어 있습니다")
            .setPriority(NotificationCompat.PRIORITY_LOW)

        startForeground(123456, notificationBuilder.build())
    }

    private fun makeAlarmNotification(alarmIntent: Intent?, alarmNote: String) {
        val pendingIntent: PendingIntent = if (alarmIntent?.action == "AlarmStop") {
            PendingIntent.getService(
                this.applicationContext,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this.applicationContext,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationBuilder = NotificationCompat.Builder(this, "$CHANNEL_ID Alarm").apply {
            setSmallIcon(R.drawable.ic_lemorning)
            setContentTitle("Lemorning 알람")
            setContentText(if (alarmNote.isNotEmpty()) "$alarmNote\n알람 끄기" else "알람 끄기")
            if (alarmIntent?.action == "AlarmStop") setContentIntent(pendingIntent)
            else setFullScreenIntent(pendingIntent, true)
            priority = NotificationCompat.PRIORITY_HIGH
            setAutoCancel(true)
        }

        NotificationManagerCompat.from(this).notify(12345, notificationBuilder.build())
    }

    private fun playAlarm(songNo: Int) {
        retrofit.create(SongApi::class.java).getSongDetail(songNo)
            .observeOn(Schedulers.newThread())
            .subscribe({
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(it.musicUrl)
                    setScreenOnWhilePlaying(true)
                    isLooping = true
                    prepareAsync() // might take long! (for buffering, etc)
                }
                mediaPlayer.setOnPreparedListener {
                    it.start()
                }
            }, {
                it.printStackTrace()
            })
    }
}