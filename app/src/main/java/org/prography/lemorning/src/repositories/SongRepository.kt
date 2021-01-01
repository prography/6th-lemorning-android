package org.prography.lemorning.src.repositories

import android.app.Application
import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.prography.lemorning.src.data.remote.services.SongService
import org.prography.lemorning.src.models.Song
import org.prography.lemorning.src.models.SongCategory
import org.prography.lemorning.src.models.SongDetail

class SongRepository(application: Application) : BaseRepository(application) {
    private val songService = SongService()

    fun loadSongs(): Single<List<Song>> {
        return songService.fetchSongs()
    }

    fun loadSongDetail(songId: Int): Single<SongDetail> {
        return songService.fetchSongDetail(songId)
    }

    fun loadSongCategories(): Single<List<SongCategory>> {
        return songService.fetchSongCategories()
    }

    fun loadRecommendedSongs(songId: Int): Single<List<SongDetail>> {
        return songService.fetchRecommendedSongs(songId)
    }

    fun registerNewSong(name: String, amount: Int, price: Int, stock: Int, alarmPath: String, img: Uri): Completable {
        return songService.registerNewSong(name, amount, price, stock, alarmPath, img)
    }
}