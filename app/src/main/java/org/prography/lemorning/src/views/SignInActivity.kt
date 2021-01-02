package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySignInBinding
import org.prography.lemorning.src.viewmodels.SignInViewModel

class SignInActivity
  : BaseActivity<ActivitySignInBinding, SignInViewModel>(R.layout.activity_sign_in) {

  override fun getViewModel(): SignInViewModel =
    ViewModelProvider(this).get(SignInViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    binding.mbtnSkipSignIn.setOnClickListener {
      showToast(getString(R.string.coming_soon))
    }
    binding.mbtnSignupSignIn.setOnClickListener {
      startActivity(Intent(this, SignUpActivity::class.java))
      overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
    }
    binding.ivKakaoSignIn.setOnClickListener { showToast(getString(R.string.coming_soon)) }
    binding.ivNaverSignIn.setOnClickListener { showToast(getString(R.string.coming_soon)) }

    /* Data Observing */
    vm.navTo.observe(this) {
      it.get()?.let {
        if (it == SignInViewModel.SIGN_IN_SUCCESS) {
          startActivity(Intent(this, MainActivity::class.java))
          finish()
        }
      }
    }
  }
}