package org.prography.lemorning.src.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmBinding
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.utils.objects.BaseViewHolder
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel

class MyAlarmsAdapter(vm: MyAlarmsViewModel)
  : BaseRecyclerAdapter<Alarm, MyAlarmsViewModel, ItemAlarmBinding>(vm, R.layout.item_alarm) {

  var isEditing: Boolean = false

  fun setIsEditing(isEditing: Boolean) {
    this.isEditing = isEditing
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
          parent: ViewGroup,
          viewType: Int
  ): BaseViewHolder<Alarm, ItemAlarmBinding> = object : BaseViewHolder<Alarm, ItemAlarmBinding>(
          DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
  ) {
    override fun initItem(item: Alarm) {
      binding.checkAlarm.visibility = if (isEditing) View.VISIBLE else View.GONE

      binding.alarmRecyclerSwitch.setOnClickListener {
        vm.updateAlarm(item, binding.alarmRecyclerSwitch.isChecked)
      }

      binding.checkAlarm.setOnClickListener {
        val prev = vm.selectedAlarms.value!!
        vm.selectedAlarms.value = if (binding.checkAlarm.isChecked) prev.plus(item) else prev.minus(item)
      }
    }
  }
}