package org.prography.lemorning.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
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
            .error(R.drawable.backgroud_black)
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
        view.setBackgroundResource(R.drawable.background_round_12_white)
        view.clipToOutline = true
    }


    @BindingAdapter("bind_img_url_opaque")
    @JvmStatic
    fun bindImgUrlOpaque(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.1f)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(100, 3)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.backgroud_black)
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
}