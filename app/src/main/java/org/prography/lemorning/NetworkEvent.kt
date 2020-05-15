package org.prography.lemorning

import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.utils.BaseResponse

class NetworkEvent : MutableLiveData<NetworkEvent.NetworkState>() {

    enum class NetworkState {
        LOADING, ERROR, FAILURE, SUCCESS
    }

    fun startLoading() {
        value = NetworkState.LOADING
    }

    fun setResponse(response: BaseResponse?) {
        if (response == null) {
            setValue(NetworkState.ERROR)
        } else {
            //setValue(if (response.getIsSuccess()) NetworkState.SUCCESS else NetworkState.FAILURE)
        }
    }
}

