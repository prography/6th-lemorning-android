package org.prography.lemorning.src.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemForyouBinding
import org.prography.lemorning.databinding.ItemForyouPlaceholderBinding
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.BaseRecyclerPlaceholderAdapter

class ForYouAdapter(
    override val layoutId: Int = R.layout.item_foryou,
    override val placeholderLayoutId: Int = R.layout.item_foryou_placeholder,
    viewModel: TrendingViewModel
) : BaseRecyclerPlaceholderAdapter<ForYou, TrendingViewModel, ItemForyouBinding, ItemForyouPlaceholderBinding>(viewModel) {



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(ForYouDecoration())
        val forYouManager = ForYouLayoutManager(recyclerView.context)
        recyclerView.layoutManager = forYouManager
        //LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    class ForYouDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            //outRect.right = (parent.paddingStart * 0.2).toInt()
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
            val startCenterPoint = getChildAt(0)?.let { it.width / 2f + paddingStart } ?: 30f
            val d1 = 0.8f * startCenterPoint
            for (i in 0 until childCount) {
                val child : View = getChildAt(i)!!
                val childMidpoint = child.let { (getDecoratedLeft(it) + getDecoratedRight(it)) / 2f}
                val d = Math.min(d1, Math.abs(startCenterPoint - childMidpoint))
                val scale = 1 - 0.2f * (d - 0.8f) / (d1 - 0.8f)

                child.scaleX = scale
                child.scaleY = scale
                child.setPadding((paddingStart * 0.4 * scale).toInt(), 0, (paddingStart * 0.4 * scale).toInt(), 0)
            }
            return scrolled
        }
    }
}

