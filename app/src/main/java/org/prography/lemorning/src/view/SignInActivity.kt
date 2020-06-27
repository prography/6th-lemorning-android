package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySignInBinding
import org.prography.lemorning.src.viewmodel.SignInViewModel

class SignInActivity(override val layoutId: Int = R.layout.activity_sign_in)
    : BaseActivity<ActivitySignInBinding, SignInViewModel>() {
    override fun getViewModel(): SignInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding.mbtnSigninSignIn.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.mbtnSigninSignIn.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.mbtnSignupSignIn.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
    }
}