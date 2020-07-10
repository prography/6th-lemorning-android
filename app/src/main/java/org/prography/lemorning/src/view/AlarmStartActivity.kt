package org.prography.lemorning.src.view

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.viewmodel.PlayAlarmViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, PlayAlarmViewModel>() {

    override fun getViewModel(): PlayAlarmViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, PlayAlarmViewModelFactory(songNo)).get(PlayAlarmViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        ApplicationClass.retrofit = Retrofit.Builder()
            .baseUrl(ApplicationClass.BASE_URL)
            .client(ApplicationClass.client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED    // 잠김 화면 위에 표시할 때 사용
            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD      // 순정 잠금 화면을 해제 할 때 사용
            or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON         // 화면을 켜진 상태로 유지
            or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        viewmodel.mediaPlayer.observe(this, Observer {
            it?.setOnPreparedListener{mediaPlayer ->
                mediaPlayer.start()
            }
        })

        alarm_off_button.setOnClickListener {
            viewmodel.mediaPlayer.value?.stop()
            finish()
        }
    }

    class PlayAlarmViewModelFactory(var songNo: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlayAlarmViewModel(songNo) as T
    }
}