package org.prography.lemorning.src.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmSettingBinding
import org.prography.lemorning.src.BindingViewHolder
import org.prography.lemorning.src.models.PlaySong

class AlarmSettingRecyclerAdapter(var songs: ArrayList<PlaySong?> = arrayListOf()):
    RecyclerView.Adapter<AlarmSettingRecyclerAdapter.AlarmSettingViewHolder>() {

    class AlarmSettingViewHolder(itemView: View) : BindingViewHolder<ItemAlarmSettingBinding>(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmSettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm_setting, parent, false)

        return AlarmSettingViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(
        holder: AlarmSettingViewHolder,
        position: Int
    ) {
        holder.binding.item = songs.get(position)
    }

}

