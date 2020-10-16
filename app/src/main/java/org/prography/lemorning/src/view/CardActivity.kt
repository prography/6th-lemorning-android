package org.prography.lemorning.src.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityCardBinding
import org.prography.lemorning.src.viewmodel.CardViewModel

class CardActivity(override val layoutId: Int = R.layout.activity_card)
    : BaseActivity<ActivityCardBinding, CardViewModel>() {

    override fun getViewModel(): CardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }
}