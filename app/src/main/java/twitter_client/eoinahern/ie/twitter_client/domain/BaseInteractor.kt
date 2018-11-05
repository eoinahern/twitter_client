package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseInteractor<T> {

    private val compositeDisposable = CompositeDisposable()

    abstract fun buildObservable(): Observable<T>

    fun execute(obs: Observer<T>) {

        buildObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(obs)
    }

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    fun unsubscribe() {
        compositeDisposable.clear()
    }
}