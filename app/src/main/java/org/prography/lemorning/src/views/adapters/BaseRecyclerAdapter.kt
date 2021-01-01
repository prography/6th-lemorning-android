package org.prography.lemorning.src.utils.objects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BaseViewModel

abstract class BaseRecyclerAdapter<I, VM : BaseViewModel, B : ViewDataBinding>(
    protected val vm: VM,
    @LayoutRes val layoutId: Int
) :
    RecyclerView.Adapter<BaseViewHolder<I, B>>(),
    IBaseRecyclerAdapter<I> {

    protected val items: ArrayList<I> = ArrayList()

    override fun setItem(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<I, B> =
        object : BaseViewHolder<I, B>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        ) {
            override fun initItem(item: I) {}
        }

    override fun onBindViewHolder(holder: BaseViewHolder<I, B>, position: Int) {
        items.getOrNull(position)?.let { holder.bindTo(it) }
    }

    override fun getItemCount(): Int = items.size
}

interface IBaseRecyclerAdapter<I> {

    fun setItem(items: List<I>)
}