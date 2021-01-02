package org.prography.lemorning.src.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.utils.objects.SingleEvent
import org.prography.lemorning.src.views.adapters.ForYouAdapter
import org.prography.lemorning.src.views.adapters.PopularAdapter
import android.util.Pair as UtilPair

class TrendingViewModel(application: Application) : BaseViewModel(application) {

  var forYouAdapter = ForYouAdapter(vm = this)
  var popularAdapter = PopularAdapter(vm = this)

  var moveToSong: MutableLiveData<SingleEvent<Int>> = MutableLiveData()

  fun onClickSong(songNo: Int, vararg pairs: UtilPair<View, String>?) {
    moveToSong.value = SingleEvent(songNo)
  }
}