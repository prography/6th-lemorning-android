package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_start.*
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmStartBinding
import org.prography.lemorning.src.viewmodel.PlayAlarmViewModel

class AlarmStartActivity(override val layoutId: Int = R.layout.activity_alarm_start) : BaseActivity<ActivityAlarmStartBinding, PlayAlarmViewModel>() {

    override fun getViewModel(): PlayAlarmViewModel {
        val songNo = intent.getIntExtra("songNo", -1)
        return ViewModelProvider(this, PlayAlarmViewModelFactory(songNo)).get(PlayAlarmViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
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