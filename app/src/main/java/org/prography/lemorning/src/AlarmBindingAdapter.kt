package org.prography.lemorning.src

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import org.prography.lemorning.src.adapters.AlarmRecyclerAdapter
import org.prography.lemorning.src.adapters.AlarmSettingRecyclerAdapter
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel


@BindingAdapter(value = ["alarms", "viewModel", "setVM"])
fun settingAdapter(view: RecyclerView, alarms: List<Alarm>?, vm: AlarmDBViewModel, setVM: AlarmViewModel) {
    view.adapter?.run {
        if (this is AlarmRecyclerAdapter) {
            if (alarms != null) {
                this.alarms = alarms
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        if (alarms != null) {
            AlarmRecyclerAdapter(
                alarms,
                vm,
                setVM
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["songs"])
fun settingAdapter(view: RecyclerView, songs: ArrayList<PlaySong?>?) {
    view.adapter?.run {
        if (this is AlarmSettingRecyclerAdapter) {
            if (songs != null) {
                this.songs = songs
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        val animator = view.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        songs?.let {
            AlarmSettingRecyclerAdapter(
                it
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["setWeek"])
fun setWeek(view:LinearLayout, week:String){
    for(i in week.indices){
        if(week[i] == '1'){
            (view[i] as TextView).setTextColor(Color.parseColor("#000000"))
        }else{
            (view[i] as TextView).setTextColor(Color.parseColor("#bfbfbf"))
        }
    }
}
