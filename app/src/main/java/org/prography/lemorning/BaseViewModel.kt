package org.prography.lemorning

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val rxDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!rxDisposable.isDisposed) {
            rxDisposable.dispose()
        }
    }
}