package org.prography.lemorning.src.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.views.adapters.CategoryAdapter
import org.prography.lemorning.src.models.SongCategory
import org.prography.lemorning.src.repositories.SongRepository
import org.prography.lemorning.src.utils.Helpers.toDisposal

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private val songRepo = SongRepository(application)

    val categoryAdapter = CategoryAdapter(this)
    val categoryList : MutableLiveData<List<SongCategory>> = MutableLiveData()

    init {
        songRepo.loadSongCategories().toDisposal(rxDisposable, {
            categoryList.value = it
        }, {
            doOnNetworkError(it)
        })
    }
}