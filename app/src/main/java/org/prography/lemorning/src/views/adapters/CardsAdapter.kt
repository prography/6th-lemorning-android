package org.prography.lemorning.src.views.adapters

import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ItemCardBinding
import org.prography.lemorning.src.models.Card
import org.prography.lemorning.src.utils.objects.BaseRecyclerAdapter

class CardsAdapter(vm: BaseViewModel) : BaseRecyclerAdapter<Card, BaseViewModel, ItemCardBinding>(vm, R.layout.item_card) {
}