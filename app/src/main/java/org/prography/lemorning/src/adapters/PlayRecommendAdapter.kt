package org.prography.lemorning.src.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemRecommendPlaySongBinding
import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.viewmodel.PlaySongViewModel
import org.prography.lemorning.utils.BaseRecyclerAdapter
import org.prography.lemorning.utils.BaseViewHolder

class PlayRecommendAdapter(override val layoutId: Int = R.layout.item_recommend_play_song, viewModel: PlaySongViewModel) :
    BaseRecyclerAdapter<PlaySong, PlaySongViewModel, ItemRecommendPlaySongBinding>(viewModel) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PlaySong, ItemRecommendPlaySongBinding> =
        object : BaseViewHolder<PlaySong, ItemRecommendPlaySongBinding>(ItemRecommendPlaySongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)) {

            override fun bindTo(item: PlaySong) {
                super.bindTo(item)

            }
        }
}