package org.prography.lemorning.src.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentSubscribeBinding
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.SubscribeViewModel

class SubscribeFragment
    : BaseFragment<FragmentSubscribeBinding, SubscribeViewModel, MainViewModel>(R.layout.fragment_subscribe) {

    override fun getViewModel(): SubscribeViewModel = ViewModelProvider(this).get(SubscribeViewModel::class.java)

    override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}