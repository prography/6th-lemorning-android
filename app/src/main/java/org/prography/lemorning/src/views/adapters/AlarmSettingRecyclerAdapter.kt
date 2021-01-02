package org.prography.lemorning.src.views.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemAlarmSettingBinding
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.viewmodels.AlarmSettingViewModel

class AlarmSettingRecyclerAdapter(vm: AlarmSettingViewModel):
    BaseRecyclerAdapter<SongDetail, AlarmSettingViewModel, ItemAlarmSettingBinding>(vm, R.layout.item_alarm_setting) {

    private var selectItem = -1

    var selectItemSongNo = -1
    var selectItemUrl = ""

    // TODO: vm 분리
    /*override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmSettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm_setting, parent, false)
        val holder = AlarmSettingViewHolder(
            view
        )
        view.alarm_setting_recycler_image.setOnClickListener{
            val position = holder.adapterPosition
            if(selectItem == position){
                selectItem = -1
                selectItemSongNo = -1
                selectItemUrl = ""
            }else{
                selectItem = position
                selectItemSongNo = songs[position]?.id ?: -1
                selectItemUrl = songs[position]?.imgUrl?: ""
            }
            notifyItemRangeChanged(0, songs.size)
        }

        return holder
    }

    override fun onBindViewHolder(
        holder: AlarmSettingViewHolder,
        position: Int
    ) {
        holder.binding.item = songs[position]

        if(position == selectItem){
            holder.binding.alarmSettingRecyclerSelect.setImageResource(R.drawable.shape_circle_yellow)
        }else{
            holder.binding.alarmSettingRecyclerSelect.setImageResource(R.drawable.background_oval_white)
        }
    }*/

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = AlarmSettingLayoutManager(recyclerView.context)
    }

    class AlarmSettingLayoutManager(context : Context)
        : LinearLayoutManager(context, HORIZONTAL, false) {
        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ): Int {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val startCenterPoint = getChildAt(0)?.let { it.width / 2f + paddingStart } ?: 30f
            val d1 = 0.8f * startCenterPoint
            for (i in 0 until childCount) {
                val child : View = getChildAt(i)!!
                val childMidpoint = child.let { (getDecoratedLeft(it) + getDecoratedRight(it)) / 2f}
                val d = Math.min(d1, Math.abs(startCenterPoint - childMidpoint))
                val scaleFactor =  (d - 0.8f) / (d1 - 0.8f)
                child.apply {
                    pivotX =
                        if (childMidpoint / startCenterPoint > 1f)
                            child.width.toFloat()
                        else if (childMidpoint / startCenterPoint < -1f)
                            0f
                        else
                            0.5f * (childMidpoint / startCenterPoint) * child.width
                    pivotY = height / 2f
                    scaleX = 1 - 0.2f * scaleFactor
                    scaleY = 1 - 0.2f * scaleFactor
                }
            }
            return scrolled
        }

        override fun onLayoutChildren(
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ) {
            super.onLayoutChildren(recycler, state)
            scrollHorizontallyBy(0, recycler, state)
        }
    }
}

