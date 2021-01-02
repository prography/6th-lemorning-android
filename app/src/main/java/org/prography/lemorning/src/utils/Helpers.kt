package org.prography.lemorning.src.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer

object Helpers {
  fun <T> Single<T>.toDisposal(
    disposable: CompositeDisposable,
    onSuccess: Consumer<in T>,
    onError: Consumer<in Throwable>
  ) {
    disposable.add(this.subscribe(onSuccess, onError))
  }

  fun <T> Observable<T>.toDisposal(
    disposable: CompositeDisposable,
    onSuccess: Consumer<in T>,
    onError: Consumer<in Throwable>,
    onComplete: Action? = null,
  ) {
    onComplete?.let {
      disposable.add(this.subscribe(onSuccess, onError, it))
    } ?: disposable.add(this.subscribe(onSuccess, onError))
  }

  fun Completable.toDisposal(
    disposable: CompositeDisposable,
    onComplete: Action,
    onError: Consumer<in Throwable>
  ) {
    disposable.add(this.subscribe(onComplete, onError))
  }

  fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    block: (T?, K?) -> R
  ): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
      result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
      result.value = block(this.value, liveData.value)
    }
    return result
  }
}