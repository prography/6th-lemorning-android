package org.prography.lemorning.src.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentTrendingBinding
import org.prography.lemorning.src.viewmodel.TrendingViewModel

class TrendingFragment(override val layoutId: Int = R.layout.fragment_trending)
    : BaseFragment<FragmentTrendingBinding, TrendingViewModel>() {


    override fun getViewModel(): TrendingViewModel {
        return ViewModelProvider(this).get(TrendingViewModel::class.java)
    }

    override fun initView() {
        binding.rvForyouTrending.adapter

        binding.mbtnSearchTrending.setOnClickListener {
            startActivity(Intent(activity, AlarmActivity::class.java))
        }
    }

}