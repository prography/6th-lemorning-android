package org.prography.lemorning.src.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemPopularBinding
import org.prography.lemorning.databinding.ItemPopularPlaceholderBinding
import org.prography.lemorning.src.models.Popular
import org.prography.lemorning.src.viewmodel.TrendingViewModel
import org.prography.lemorning.utils.BaseRecyclerAdapter
import org.prography.lemorning.utils.BaseRecyclerPlaceholderAdapter
import org.prography.lemorning.utils.BaseViewHolder
import org.prography.lemorning.utils.BaseViewPlaceHolder
import android.util.Pair as UtilPair

class PopularAdapter(
    override val layoutId: Int = R.layout.item_popular,
    override val placeholderLayoutId: Int = R.layout.item_popular_placeholder,
    viewModel: TrendingViewModel
) : BaseRecyclerPlaceholderAdapter<Popular, TrendingViewModel, ItemPopularBinding, ItemPopularPlaceholderBinding>(viewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            BaseViewPlaceHolder<Popular, ItemPopularBinding, ItemPopularPlaceholderBinding> {
        return when(viewType) {
            TYPE_REALVIEW -> object : BaseViewPlaceHolder<Popular, ItemPopularBinding, ItemPopularPlaceholderBinding>(
                binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                viewType = viewType
            ) {
                override fun initItem(item: Popular?) {
                    super.initItem(item)
                    binding?.let {
                        it.root.setOnClickListener { it1 ->
                            item?.let { it2 ->
                                viewmodel.onClickSong(it2.no,
                                    UtilPair.create(it.ivThumbnailItemPopular , "thumbnail_album"),
                                    UtilPair.create(it.tvTitleItemPopular , "title_album"),
                                    UtilPair.create(it.tvCategoryItemPopular , "category_album"))
                            }
                        }
                    }
                }
            }
            else -> object : BaseViewPlaceHolder<Popular, ItemPopularBinding, ItemPopularPlaceholderBinding>(
                placeholder = ItemPopularPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                viewType = viewType
            ) {}
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