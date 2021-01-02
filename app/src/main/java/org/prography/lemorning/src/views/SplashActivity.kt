package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySplashBinding
import org.prography.lemorning.src.viewmodels.SplashViewModel

class SplashActivity
  : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
  override fun getViewModel(): SplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

  override fun initView(savedInstanceState: Bundle?) {
    vm.successLogin.observe(this) {
      it.get()?.let {
        startActivity(Intent(this, if (it) MainActivity::class.java else SignInActivity::class.java))
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        finish()
      }
    }
  }
}