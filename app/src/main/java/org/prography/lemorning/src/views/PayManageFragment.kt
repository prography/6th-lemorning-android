package org.prography.lemorning.src.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentPayManageBinding
import org.prography.lemorning.src.viewmodels.MainViewModel
import org.prography.lemorning.src.viewmodels.PayManageViewModel

class PayManageFragment
  : BaseFragment<FragmentPayManageBinding, PayManageViewModel, MainViewModel>(R.layout.fragment_pay_manage) {

  override fun getViewModel(): PayManageViewModel = ViewModelProvider(this).get(PayManageViewModel::class.java)

  override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    binding.rvCard.adapter = vm.cardsAdapter
    binding.tvAddCard.setOnClickListener {
      findNavController().navigate(R.id.fg_card_register)
    }

    pvm.myCards.observe(this) { vm.cardsAdapter.setItem(it) }
  }
}