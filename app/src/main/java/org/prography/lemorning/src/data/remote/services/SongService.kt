package org.prography.lemorning.src.data.remote.services

import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.prography.lemorning.ApplicationClass.Companion.retrofit
import org.prography.lemorning.src.data.remote.apis.SongApi
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.models.SongCategory
import org.prography.lemorning.src.models.SongDetail
import org.prography.lemorning.src.utils.Constants
import org.prography.lemorning.src.utils.objects.ApiError
import java.io.File

class SongService: BaseService() {
    private val songApi = retrofit.create(SongApi::class.java)

    fun fetchSongs(): Single<List<Song>> {
        return songApi.getSongs()
            .map {
                if (it.code != 200) throw ApiError(it)
                it.data
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchSongCategories(): Single<List<SongCategory>> {
        return songApi.getCategory()
            .map {
                if (it.code != 200) throw ApiError(it)
                it.data
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchSongDetail(songId: Int): Single<SongDetail> {
        return songApi.getSongDetail(songId)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchRecommendedSongs(songId: Int): Single<List<SongDetail>> {
        return songApi.getRecommendedSongs(songId)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerNewSong(name: String, amount: Int, price: Int, stock: Int, alarm: String, img: Uri): Completable {
        return songApi.registerSong(
            name = name.toRequestBody(),
            number = amount.toString().toRequestBody(),
            price = price.toString().toRequestBody(),
            stock = stock.toString().toRequestBody(),
            alarm = MultipartBody.Part.createFormData("alarm", alarm, File(alarm).asRequestBody(Constants.MEDIA_TYPE_FORM_DATA.toMediaType())),
            image = MultipartBody.Part.createFormData("image", File(img.path!!).name,
                File(img.path!!).asRequestBody(Constants.MEDIA_TYPE_FORM_DATA.toMediaType()))
        )
            .flatMapCompletable {
                if (it.code != 200) throw ApiError(it)
                Completable.complete()
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}