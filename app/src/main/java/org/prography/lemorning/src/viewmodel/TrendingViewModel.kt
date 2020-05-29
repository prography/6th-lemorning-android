package org.prography.lemorning.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.adapters.ForYouAdapter
import org.prography.lemorning.src.adapters.PopularAdapter
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.repository.networks.TrendingApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingViewModel : BaseViewModel() {

    var forYouAdapter = ForYouAdapter(viewModel = this)
    var popularAdapter = PopularAdapter()

    var forYous : LiveData<ArrayList<ForYou>> = getForYouList()

    fun getForYouList() : LiveData<ArrayList<ForYou>> {
        var result = MutableLiveData<ArrayList<ForYou>>()
        ApplicationClass.retrofit.create(TrendingApiService::class.java).getForYous().enqueue(object : Callback<ArrayList<ForYou>> {

            override fun onFailure(call: Call<ArrayList<ForYou>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<ForYou>>,
                response: Response<ArrayList<ForYou>>
            ) {
                var baseResponse = response.body();
                if (!response.isSuccessful || baseResponse == null) {
                    return
                }
                result.value = baseResponse
            }
        })
        return result
    }
}