package org.prography.lemorning.src.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemForyouBinding
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.utils.objects.BaseViewHolder
import org.prography.lemorning.src.viewmodels.TrendingViewModel

class ForYouAdapter(
    vm: TrendingViewModel
) : BaseRecyclerAdapter<Song, TrendingViewModel, ItemForyouBinding>(vm, R.layout.item_foryou) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Song, ItemForyouBinding> = object : BaseViewHolder<Song, ItemForyouBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: Song) {
            binding.root.setOnClickListener { item.let { vm.onClickSong(it.id, null) } }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = ForYouLayoutManager(recyclerView.context)
    }

    class ForYouLayoutManager(context : Context)
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

