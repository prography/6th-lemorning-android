package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentAlarmBinding
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AlarmTheme);
        val localInflater = inflater.cloneInContext(contextThemeWrapper);

        val binding = DataBindingUtil.inflate<FragmentAlarmBinding>(localInflater, R.layout.fragment_alarm, container, false)
        binding.alarmViewModel = ViewModelProvider(this)
            .get(AlarmDBViewModel::class.java)
        binding.dateVM = ViewModelProvider(this)
            .get(AlarmViewModel::class.java)
        binding.lifecycleOwner = this

        binding.alarmImage.setOnClickListener {
            val intent = Intent(
                container?.context,
                AlarmSettingActivity::class.java
            )
            startActivity(intent)
        }

        return binding.root
    }
}