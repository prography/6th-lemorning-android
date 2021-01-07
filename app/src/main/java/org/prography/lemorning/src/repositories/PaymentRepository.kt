package org.prography.lemorning.src.repositories

import android.app.Application
import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.src.data.remote.services.PaymentService
import org.prography.lemorning.src.models.Card

class PaymentRepository(application: Application) : BaseRepository(application) {
  private val paymentService = PaymentService()

  fun loadCreditCards(): Single<List<Card>> {
    return paymentService.fetchCreditCards()
  }
}