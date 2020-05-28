package org.prography.lemorning.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.models.ForYou

class TrendingViewModel : BaseViewModel() {

    fun getForYouList() : LiveData<ArrayList<ForYou>> {
        return MutableLiveData(ArrayList())
    }
}