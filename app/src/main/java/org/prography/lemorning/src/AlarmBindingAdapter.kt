package org.prography.lemorning.src

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel

@BindingAdapter(value = ["alarms", "viewModel"])
fun settingAdapter(view: RecyclerView, alarms: List<Alarm>?, vm: AlarmDBViewModel) {
    view.adapter?.run {
        if (this is AlarmRecyclerAdapter) {
            if (alarms != null) {
                this.alarms = alarms
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        if (alarms != null) {
            AlarmRecyclerAdapter(alarms, vm).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["setWeek"])
fun setWeek(view:LinearLayout, week:String){
    for(i in week.indices){
        if(week[i] == '0'){
            (view[i] as TextView).setTextColor(Color.parseColor("#50666666"))
        }
    }
}
