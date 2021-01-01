package org.prography.lemorning.src.views

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityCardBinding
import org.prography.lemorning.src.viewmodels.CardViewModel

class CardActivity
    : BaseActivity<ActivityCardBinding, CardViewModel>(R.layout.activity_card) {

    override fun getViewModel(): CardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        binding.tvAddCard.setOnClickListener {
            startActivity(Intent(this, AddCardActivity::class.java))
        }

        binding.ivCloseCard.setOnClickListener {
            finish()
        }
    }
}