package org.prography.lemorning.src.views.components

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentStep3SignUpBinding
import org.prography.lemorning.src.viewmodels.SignUpViewModel

class SignUpStep3Fragment
    : BaseFragment<FragmentStep3SignUpBinding, SignUpViewModel, SignUpViewModel>(R.layout.fragment_step3_sign_up) {

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun getParentViewModel(): SignUpViewModel =
        ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}