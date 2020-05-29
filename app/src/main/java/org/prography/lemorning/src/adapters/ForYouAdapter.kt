package org.prography.lemorning.src.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemForyouBinding
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.BaseListAdapter

class ForYouAdapter(
    override val layoutId: Int = R.layout.item_foryou,
    var viewModel: TrendingViewModel
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
    }

    class ForYouDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

        }
    }
}