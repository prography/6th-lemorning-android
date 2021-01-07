package org.prography.lemorning.src.views.adapters

import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemCategoryBinding
import org.prography.lemorning.src.models.SongCategory
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter
import org.prography.lemorning.src.viewmodels.SearchViewModel

class CategoryAdapter(vm: SearchViewModel) : BaseRecyclerAdapter<SongCategory, SearchViewModel, ItemCategoryBinding>(vm, R.layout.item_category) {
}