package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseInteractor<T> {

    private val compositeDisposable = CompositeDisposable()

    abstract fun buildObservable(): Observable<T>

    fun execute(obs: Observer<T>) {

    }

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    fun unsubscribe() {
        compositeDisposable.clear()
    }


}