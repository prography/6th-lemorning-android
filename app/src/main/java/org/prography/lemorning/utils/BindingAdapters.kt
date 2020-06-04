package org.prography.lemorning.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import org.prography.lemorning.R
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("bind_img_url_circle")
    @JvmStatic
    fun bindImgUrlCircle(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.shape_black)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(view)
        view.clipToOutline = true
    }

    @BindingAdapter("bind_img_url_round_4")
    @JvmStatic
    fun bindImgUrlRound4(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.img_foryou_sample1)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(view)
        view.setBackgroundResource(R.drawable.shape_round_12_white)
        view.clipToOutline = true
    }


    @BindingAdapter("bind_img_url_opaque")
    @JvmStatic
    fun bindImgUrlOpaque(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(BlurTransformation(25, 3))
            .error(R.drawable.shape_black)
            .centerCrop()
            .into(view)
        view.clipToOutline = true
    }

    @BindingAdapter("time")
    @JvmStatic
    fun timeToDay(view : TextView, time : Long) {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.US)
        view.text = sdf.format(Date(time))
    }

    @BindingAdapter("m_ss")
    @JvmStatic
    fun intToM_ss(view : TextView, time : Int) {
        val sdf = SimpleDateFormat("mm:ss", Locale.US)
        view.text = sdf.format(Date(time.toLong()))
    }
}