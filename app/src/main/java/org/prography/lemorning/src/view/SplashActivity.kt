package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySplashBinding
import org.prography.lemorning.src.viewmodel.SplashViewModel

class SplashActivity(override val layoutId: Int = R.layout.activity_splash)
    : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getViewModel(): SplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        Thread {
            Thread.sleep(1000)
            startActivity(Intent(this, SignInActivity::class.java))
        }.start()
    }
}