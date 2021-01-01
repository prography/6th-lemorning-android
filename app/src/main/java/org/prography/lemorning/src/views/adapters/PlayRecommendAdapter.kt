package org.prography.lemorning.src.views.adapters

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemRecommendPlaySongBinding
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.utils.objects.BaseViewHolder
import org.prography.lemorning.src.viewmodels.SongDetailViewModel

class PlayRecommendAdapter(
    vm: SongDetailViewModel) :
    BaseRecyclerAdapter<SongDetail, SongDetailViewModel, ItemRecommendPlaySongBinding>(
        vm,
        R.layout.item_recommend_play_song
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SongDetail, ItemRecommendPlaySongBinding> = object : BaseViewHolder<SongDetail, ItemRecommendPlaySongBinding>(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
    ) {
        override fun initItem(item: SongDetail) {
            binding.root.setOnClickListener {
                item.id.let {
                    vm.unregisterTimerOnMediaPlayer()
                    vm.mediaPlayer.value?.release()
                    vm.songDetail = vm.getSongDetail(it)
                    vm.nextSongList = vm.getNextSongs(it)
                } }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = PlayRecommendLayoutManager(recyclerView.context)
        LinearSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(PlayRecommendItemDecoration())
    }

    class PlayRecommendItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = when (parent.getChildAdapterPosition(view)) {
                parent.adapter?.itemCount -> parent.paddingStart / 5
                else -> parent.paddingStart / 5
            }
            outRect.left = when (parent.getChildAdapterPosition(view)) {
                0 -> parent.paddingStart / 5
                else -> parent.paddingStart / 5
            }
        }
    }

    class PlayRecommendLayoutManager(context : Context) : LinearLayoutManager(context, HORIZONTAL, false) {
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
                    alpha = 1 - 0.2f * scaleFactor
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