package org.prography.lemorning.src.view.signup

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySignUpBinding
import org.prography.lemorning.src.viewmodel.SignUpViewModel

class SignUpActivity(override val layoutId: Int = R.layout.activity_sign_up)
    : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {
    override fun getViewModel(): SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}