package org.prography.lemorning

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.prography.lemorning.config.XAccessTokenInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    // 테스트 서버 주소
    companion object {
        val BASE_URL = "http://192.168.0.213:8000/"
        // 실서버 주소
        //    public static String BASE_URL = "https://template.prography.org/";

        lateinit var sSharedPreferences: SharedPreferences

        // SharedPreferences 키 값
        val TAG = "LE-MORNING"

        // JWT Token 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        // LANGUAGE 설정 값
        val LANGUAGE = "LANGUAGE"

        // 날짜 형식
        val DATE_FORMAT_SERVER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

        // Retrofit 인스턴스
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}