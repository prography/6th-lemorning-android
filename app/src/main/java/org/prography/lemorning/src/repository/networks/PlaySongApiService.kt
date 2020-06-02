package org.prography.lemorning.src.repository.networks

import org.prography.lemorning.src.models.PlaySong
import org.prography.lemorning.src.models.Popular
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaySongApiService {
    @GET("/api/shop/{songNo}")
    fun getPlaySong(@Path("songNo") songNo: Int) : Call<PlaySong>
}