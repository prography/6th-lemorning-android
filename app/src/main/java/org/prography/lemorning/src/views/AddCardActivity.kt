package org.prography.lemorning.src.views

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAddCardBinding
import org.prography.lemorning.src.viewmodels.CardRegisterViewModel

class AddCardActivity
    : BaseActivity<ActivityAddCardBinding, CardRegisterViewModel>( R.layout.activity_add_card) {

    override fun getViewModel(): CardRegisterViewModel = ViewModelProvider(this).get(CardRegisterViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

    }

}