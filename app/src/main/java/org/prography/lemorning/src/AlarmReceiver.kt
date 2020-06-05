package org.prography.lemorning.src

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.prography.lemorning.src.view.AlarmStartActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmIntent = Intent(context, AlarmStartActivity::class.java)
        alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val songNo = intent?.getIntExtra("songNo", -1)
        alarmIntent.putExtra("songNo", songNo)
        context?.startActivity(alarmIntent)
    }
}