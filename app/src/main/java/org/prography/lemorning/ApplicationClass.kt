package org.prography.lemorning

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.prography.lemorning.src.data.remote.XAccessTokenInterceptor
import org.prography.lemorning.src.utils.Constants.APP_TAG
import org.prography.lemorning.src.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    // 테스트 서버 주소
    companion object {
        lateinit var sharedPref: SharedPreferences

        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (message.startsWith("{") && message.endsWith("}")) {
                    Logger.t("OKHTTP").json(message)
                } else {
                    Log.i("OKHTTP", message)
                }
            }
        }).apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(6000, TimeUnit.MILLISECONDS)
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

        val noTokenClient: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(6000, TimeUnit.MILLISECONDS)
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .build()

        var noTokenRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(noTokenClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        sharedPref = applicationContext.getSharedPreferences(APP_TAG, MODE_PRIVATE)

        Logger.addLogAdapter(object : AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .build()
        ) {
            override fun isLoggable(priority: Int, tag: String?): Boolean = BuildConfig.DEBUG
        })

        FirebaseApp.initializeApp(this)
    }
}