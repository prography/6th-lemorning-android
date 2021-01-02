package org.prography.lemorning.src.viewmodels

import android.app.Application
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.views.adapters.CardsAdapter

class PayManageViewModel(application: Application) : BaseViewModel(application) {
  val cardsAdapter = CardsAdapter(this)
}