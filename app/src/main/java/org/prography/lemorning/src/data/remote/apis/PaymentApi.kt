package org.prography.lemorning.src.data.remote.apis

import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.src.models.Card
import org.prography.lemorning.src.models.CardRegisterParam
import org.prography.lemorning.src.utils.objects.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApi {
  @GET("/card/list")
  fun getCardList(): Single<BaseResponse<List<Card>>>

  @POST("/card/save")
  fun registerNewCard(@Body param: CardRegisterParam): Single<BaseResponse<Any>>

  @POST("/card/delete/{cardId}")
  fun deleteCard(): Single<BaseResponse<Any>>
}