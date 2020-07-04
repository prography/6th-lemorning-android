package org.prography.lemorning.src.view.signup

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.databinding.FragmentStep3SignUpBinding
import org.prography.lemorning.src.viewmodel.SignUpViewModel

class Step3Fragment(override val layoutId: Int = R.layout.fragment_step3_sign_up)
    : BaseFragment<FragmentStep3SignUpBinding, SignUpViewModel>() {

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_signup)).get(SignUpViewModel::class.java)

    override fun initView() {

    }
}