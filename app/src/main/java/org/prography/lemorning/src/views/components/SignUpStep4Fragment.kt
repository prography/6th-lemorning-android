package org.prography.lemorning.src.views.components

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentStep4SignUpBinding
import org.prography.lemorning.src.viewmodels.SignUpViewModel

class SignUpStep4Fragment : BaseFragment<FragmentStep4SignUpBinding, SignUpViewModel, SignUpViewModel>(R.layout.fragment_step4_sign_up) {

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun getParentViewModel(): SignUpViewModel =
        ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}