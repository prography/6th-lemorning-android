package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentMypageBinding
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.MyPageViewModel

class MyPageFragment
  : BaseFragment<FragmentMypageBinding, MyPageViewModel, MainViewModel>(R.layout.fragment_mypage) {

  override fun getViewModel(): MyPageViewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

  override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    binding.layoutMusicMypage.setOnClickListener {
      startActivity(Intent(requireActivity(), CreateSongActivity::class.java))
    }

    binding.tvMyCardMypage.setOnClickListener {
      findNavController().navigate(R.id.fg_pay_manage)
    }
  }
}