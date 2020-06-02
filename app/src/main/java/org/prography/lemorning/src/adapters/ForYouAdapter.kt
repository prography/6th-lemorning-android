package org.prography.lemorning.src.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemForyouBinding
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.BaseListAdapter

class ForYouAdapter(
    override val layoutId: Int = R.layout.item_foryou,
    viewModel: TrendingViewModel
) : BaseListAdapter<ForYou, TrendingViewModel, ItemForyouBinding>(DIFF_CALLBACK, viewModel) {

    object DIFF_CALLBACK : DiffUtil.ItemCallback<ForYou>() {
        override fun areItemsTheSame(oldItem: ForYou, newItem: ForYou): Boolean =
            oldItem.no == newItem.no

        override fun areContentsTheSame(oldItem: ForYou, newItem: ForYou): Boolean =
            oldItem.no == newItem.no && oldItem.title == newItem.title && oldItem.category == newItem.category
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(ForYouDecoration())
        recyclerView.layoutManager = ForYouLayoutManager(recyclerView.context)
    }

    class ForYouDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = 0
        }
    }

    class ForYouLayoutManager(context : Context)
        : LinearLayoutManager(context, HORIZONTAL, false) {
        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ): Int {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midPoint = width / 2f
            val d0 = 0.8f
            val d1 = 0.9f * midPoint
            val s0 = 1f;
            val s1 = 1f - 0.2f;
            for (i in 0 until childCount) {
                val child : View = getChildAt(i)!!
                val childMidpoint = child.let { (getDecoratedRight(it) + getDecoratedLeft(it)) / 2f}
                val d = Math.min(d1, Math.abs(midPoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
            return scrolled
        }
    }
}

