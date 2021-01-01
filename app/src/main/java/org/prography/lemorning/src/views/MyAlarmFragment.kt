package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentMyAlarmsBinding
import org.prography.lemorning.src.views.adapters.MyAlarmsAdapter
import org.prography.lemorning.src.viewmodels.MyAlarmsViewModel
import org.prography.lemorning.src.viewmodels.MainViewModel

class MyAlarmFragment: BaseFragment<FragmentMyAlarmsBinding, MyAlarmsViewModel, MainViewModel>(R.layout.fragment_my_alarms) {

    override fun getViewModel(): MyAlarmsViewModel = ViewModelProvider(this).get(MyAlarmsViewModel::class.java)

    override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.rvMyAlarms.adapter = vm.myAlarmsAdapter
        binding.alarmEditButton.setOnClickListener {
            if(binding.alarmEditButton.text == "삭제"){
                binding.alarmEditButton.text = "관리"
                (binding.rvMyAlarms.adapter as MyAlarmsAdapter).flag = false
            }else{
                binding.alarmEditButton.text = "삭제"
                (binding.rvMyAlarms.adapter as MyAlarmsAdapter).flag = true
                Toast.makeText(context, "삭제할 알람을 길게 클릭해주세요", Toast.LENGTH_LONG).show()
            }
        }
        binding.alarmAdd.setOnClickListener {
            findNavController().navigate(R.id.fg_alarm_setting)
        }

        pvm.myAlarms.observe(this) { vm.myAlarmsAdapter.setItem(it) }
    }
}