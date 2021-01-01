package org.prography.lemorning.src.utils.objects

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BR

abstract class BaseViewHolder<I, B : ViewDataBinding>(val binding: B) :
    RecyclerView.ViewHolder(binding.root),
    IBaseViewHolder<I> {

    open fun bindTo(item: I) {
        binding.setVariable(BR.item, item)
        initItem(item)
    }
}

interface IBaseViewHolder<I> {
    fun initItem(item: I)
}