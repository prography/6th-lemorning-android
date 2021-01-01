package org.prography.lemorning.src.views.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmBinding
import org.prography.lemorning.src.AlarmReceiver
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.utils.objects.BaseViewHolder
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel

class MyAlarmsAdapter(vm: MyAlarmsViewModel)
    : BaseRecyclerAdapter<Alarm, MyAlarmsViewModel, ItemAlarmBinding>(vm, R.layout.item_alarm) {
    var flag = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Alarm, ItemAlarmBinding> = object: BaseViewHolder<Alarm, ItemAlarmBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: Alarm) {
            binding.alarmRecyclerSwitch.setOnClickListener{
                if(binding.alarmRecyclerSwitch.isChecked){
                    vm.updateOn(item, true)
                    val context = binding.root.context.applicationContext
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java).apply {
                        putExtra("id", item.id)
                        putExtra("songNo", item.songNo)
                        putExtra("week", item.week)
                        putExtra("date", item.date)
                        putExtra("alarmNote", item.alarmNote)
                    }
                    val pendingIntent =
                        item.id.let {alarmId ->
                            PendingIntent.getBroadcast(context,
                                alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                        }
                    pendingIntent?.let { it1 ->
                        alarmViewModel.setAlarmManager(alarm,
                            it1, alarmManager)
                    }
                } else {
                    vm.updateOn(item, false)
                    vm.cancelAlarm(item)
                }
            }

            binding.alarmRecyclerCard.setOnLongClickListener{
                if(flag){
                    vm.cancelAlarm(item)
                    vm.delete(item)
                    true
                } else false
            }
        }
    }
}