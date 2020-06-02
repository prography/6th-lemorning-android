package org.prography.lemorning.src.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemPopularBinding
import org.prography.lemorning.src.models.Popular
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.BaseRecyclerAdapter
import org.prography.lemorning.utils.BaseViewHolder

class PopularAdapter(
    override val layoutId: Int = R.layout.item_popular,
    viewModel: TrendingViewModel
) : BaseRecyclerAdapter<Popular, TrendingViewModel, ItemPopularBinding>(viewModel) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Popular, ItemPopularBinding> = object
        : BaseViewHolder<Popular, ItemPopularBinding>(
        ItemPopularBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        override fun initItem(item: Popular) {
            super.initItem(item)
            binding.root.setOnClickListener { viewmodel.onClickSong(item.no) }
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(PopularItemDecoration())
    }

    class PopularItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = 0
        }
    }
}