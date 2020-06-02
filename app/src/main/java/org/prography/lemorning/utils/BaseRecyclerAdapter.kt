package org.prography.lemorning.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.prography.lemorning.BaseViewModel

abstract class BaseRecyclerAdapter<I, VM : BaseViewModel, B : ViewDataBinding>(protected var viewmodel : VM) :
    RecyclerView.Adapter<BaseViewHolder<I, B>>(), BaseRecyclerAdapterInterface<I> {

    var items: ArrayList<I> = ArrayList()

    override fun setItem(items: ArrayList<I>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<I, B> =
        object : BaseViewHolder<I, B>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        ) {}

    override fun onBindViewHolder(holder: BaseViewHolder<I, B>, position: Int) {
        val item: I = items.get(position)
        if (item != null) {
            holder.bindTo(item)
        }
    }

    override fun getItemCount(): Int = items.size

}

interface BaseRecyclerAdapterInterface<I> {
    @get:LayoutRes
    val layoutId: Int

    fun setItem(items : ArrayList<I>)
}