package org.prography.lemorning.src.utils.components

import androidx.lifecycle.MutableLiveData
import com.google.firebase.crashlytics.FirebaseCrashlytics

class NetworkEvent : MutableLiveData<NetworkEvent.NetworkState>() {
    enum class NetworkState {
        LOADING, COMPLETE
    }

    fun start() {
        value = NetworkState.LOADING
    }

    fun done() {
        value = NetworkState.COMPLETE
    }
}