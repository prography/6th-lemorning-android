package org.prography.lemorning.utils.components

import androidx.lifecycle.MutableLiveData

class NetworkEvent : MutableLiveData<NetworkEvent.NetworkState>() {
    enum class NetworkState {
        LOADING, FAILURE, SUCCESS, ERROR
    }

    fun startLoading() {
        value =
            NetworkState.LOADING
    }

    fun handleResponse(response : Any?) {

    }
}