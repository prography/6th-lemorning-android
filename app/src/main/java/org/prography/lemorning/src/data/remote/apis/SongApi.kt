package org.prography.lemorning.src.data.remote.apis

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.prography.lemorning.src.models.SongCategory
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.utils.objects.BaseResponse
import retrofit2.http.*

interface SongApi {
    @GET("/product/products")
    fun getSongs() : Single<BaseResponse<List<Song>>>

    @GET("/shop/lists/{songNo}")
    fun getSongDetail(@Path("songNo") songNo: Int) : Single<SongDetail>

    @GET("/shop/lists/{songNo}/recommand_category")
    fun getRecommendedSongs(@Path("songNo") songNo: Int) : Single<List<SongDetail>>

    @Multipart
    @POST("/shop/product")
    fun registerSong(@Part("name") name: RequestBody?,
                     @Part("number") number: RequestBody?,
                     @Part("price") price: RequestBody?,
                     @Part("stock") stock: RequestBody?,
                     @Part image: MultipartBody.Part?,
                     @Part alarm: MultipartBody.Part?) : Single<BaseResponse<Any>>

    @GET("/api/category")
    fun getCategory() : Single<BaseResponse<List<SongCategory>>>
}