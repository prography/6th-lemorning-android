package org.prography.lemorning.src

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.prography.lemorning.src.view.AlarmStartActivity
import java.util.*

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getStringExtra("week").let {
            if(it?.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1) == '0'){
                return
            }
            if(it == null) return
        }

        val alarmIntent = Intent(context, AlarmStartActivity::class.java)
        alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val songNo = intent?.getIntExtra("songNo", -1)
        alarmIntent.putExtra("songNo", songNo)
        context?.startActivity(alarmIntent)
    }
}