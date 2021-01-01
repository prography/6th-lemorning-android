package org.prography.lemorning.src.data.remote.services

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.ApplicationClass.Companion.retrofit
import org.prography.lemorning.src.data.remote.apis.PaymentApi
import org.prography.lemorning.src.models.Card
import org.prography.lemorning.src.utils.objects.ApiError

class PaymentService: BaseService() {
    private val paymentApi = retrofit.create(PaymentApi::class.java)

    fun fetchCreditCards(): Single<List<Card>> {
        return paymentApi.getCardList().map {
            if (it.code != 200) throw ApiError(it)
            it.data
        }
            .observeOn(AndroidSchedulers.mainThread())
    }
}