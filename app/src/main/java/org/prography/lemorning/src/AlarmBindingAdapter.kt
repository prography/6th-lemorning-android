package org.prography.lemorning.src

import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.R
import org.prography.lemorning.src.adapters.AlarmRecyclerAdapter
import org.prography.lemorning.src.adapters.AlarmSettingRecyclerAdapter
import org.prography.lemorning.src.model.Alarm
import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.repository.networks.PlaySongApiService
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@BindingAdapter(value = ["alarms", "viewModel", "setVM"])
fun settingAdapter(view: RecyclerView, alarms: List<Alarm>?, vm: AlarmDBViewModel, setVM: AlarmViewModel) {
    view.adapter?.run {
        if (this is AlarmRecyclerAdapter) {
            if (alarms != null) {
                this.alarms = alarms
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        if (alarms != null) {
            AlarmRecyclerAdapter(
                alarms,
                vm,
                setVM
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["songs"])
fun settingAdapter(view: RecyclerView, songs: ArrayList<PlaySong?>?) {
    view.adapter?.run {
        if (this is AlarmSettingRecyclerAdapter) {
            if (songs != null) {
                this.songs = songs
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        songs?.let {
            AlarmSettingRecyclerAdapter(
                it
            ).apply { view.adapter = this }
        }
    }
}

@BindingAdapter(value = ["song"])
fun getSong(view: ImageView, songNo: Int) {

    ApplicationClass.retrofit.create(PlaySongApiService::class.java).getPlaySong(songNo).enqueue(object :
        Callback<PlaySong> {
        override fun onFailure(call: Call<PlaySong>, t: Throwable) {
            t.printStackTrace()
        }

        override fun onResponse(call: Call<PlaySong>, response: Response<PlaySong>) {
            val playSong : PlaySong? = response.body() ?: return
            val url = playSong?.imgUrl
            Glide.with(view.context)
                .load(url)
                .error(R.drawable.shape_black)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(view)
            view.clipToOutline = true
        }
    })
}

@BindingAdapter(value = ["setWeek"])
fun setWeek(view:LinearLayout, week:String){
    for(i in week.indices){
        if(week[i] == '1'){
            (view[i] as TextView).setTextColor(Color.parseColor("#000000"))
        }else{
            (view[i] as TextView).setTextColor(Color.parseColor("#bfbfbf"))
        }
    }
}
