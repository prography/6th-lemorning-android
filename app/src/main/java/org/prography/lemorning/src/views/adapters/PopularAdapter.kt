package org.prography.lemorning.src.views.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemPopularBinding
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.utils.components.SimpleMessageDialog
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.utils.objects.BaseViewHolder
import org.prography.lemorning.src.viewmodels.TrendingViewModel
import android.util.Pair as UtilPair

class PopularAdapter(
  vm: TrendingViewModel
) : BaseRecyclerAdapter<Song, TrendingViewModel, ItemPopularBinding>(vm, R.layout.item_popular) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder<Song, ItemPopularBinding> = object : BaseViewHolder<Song, ItemPopularBinding>(
    DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
  ) {
    override fun initItem(item: Song) {
      binding.let {
        it.root.setOnClickListener { it1 ->
          item.let { it2 ->
            vm.onClickSong(it2.id,
              UtilPair.create(it.ivThumbnailItemPopular, "thumbnail_album"),
              UtilPair.create(it.tvTitleItemPopular, "title_album"),
              UtilPair.create(it.tvCategoryItemPopular, "category_album"))
          }
        }
        it.ivLikeItemPopular.setOnClickListener { v ->
          SimpleMessageDialog(
            context = it.root.context,
            message = it.root.context.getString(R.string.coming_soon)
          ).show()
        }
      }
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