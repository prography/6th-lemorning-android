package org.prography.lemorning.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.prography.lemorning.R

object BindingAdapters {

    @BindingAdapter("bind_img_url_round_4")
    @JvmStatic
    fun bindImgUrlRound4(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.img_foryou_sample1)
            .error(R.drawable.img_foryou_sample1)
            .centerCrop()
            .into(view)
        view.setBackgroundResource(R.drawable.background_round_12_white)
        view.clipToOutline = true
    }

}