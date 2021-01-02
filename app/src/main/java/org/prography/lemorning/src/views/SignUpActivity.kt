package org.prography.lemorning.src.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySignUpBinding
import org.prography.lemorning.src.viewmodels.SignUpViewModel

class SignUpActivity
  : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {
  override fun getViewModel(): SignUpViewModel =
    ViewModelProvider(this).get(SignUpViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    /* Data Observing */
    vm.navTo.observe(this) {
      it.get()?.let {
        if (it != -1) {
          findNavController(R.id.fg_signup).navigate(it)
        } else finish()
      }
    }
  }

  override fun onBackPressed() {
    if (findNavController(R.id.fg_signup).currentDestination?.id == R.id.fg_step4_signup) {
      finish()
      supportFinishAfterTransition()
      overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
      return
    }
    super.onBackPressed()
  }
}