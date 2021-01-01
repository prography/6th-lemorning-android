package org.prography.lemorning.src.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentCardRegisterBinding
import org.prography.lemorning.src.viewmodels.CardRegisterViewModel
import org.prography.lemorning.src.viewmodels.MainViewModel

class AddCardFragment
    : BaseFragment<FragmentCardRegisterBinding, CardRegisterViewModel, MainViewModel>(R.layout.fragment_card_register) {

    override fun getViewModel(): CardRegisterViewModel = ViewModelProvider(this).get(CardRegisterViewModel::class.java)

    override fun getParentViewModel(): MainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.mbtnSubmit.setOnClickListener {
            findNavController().navigate(R.id.fg_pay_manage)
        }
    }
}