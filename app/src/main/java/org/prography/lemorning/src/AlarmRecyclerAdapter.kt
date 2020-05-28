package org.prography.lemorning.src

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BindingViewHolder
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmBinding
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.viewmodel.AlarmViewModel

class AlarmRecyclerAdapter(var alarms: List<Alarm> = arrayListOf(), val vm: AlarmViewModel): RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(itemView: View) : BindingViewHolder<ItemAlarmBinding>(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.binding.alarm = alarms[position]
        holder.binding.vm = vm
    }
}