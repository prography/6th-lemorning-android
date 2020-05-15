package org.prography.lemorning.src.view

import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityMainBinding
import org.prography.lemorning.src.viewmodel.MainViewModel

class MainActivity(override val layoutId: Int = R.layout.activity_main)
    : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewModel(): MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    override fun initView() {
        TODO("Not yet implemented")
    }

}
