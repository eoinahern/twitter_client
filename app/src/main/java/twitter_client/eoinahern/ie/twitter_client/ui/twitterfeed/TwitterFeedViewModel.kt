package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import java.net.SocketTimeoutException
import javax.inject.Inject


@PerScreen
class TwitterFeedViewModel @Inject constructor(private val getTwitterDataInteractor: GetTwitterDataInteractor) :
    ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    private val errorState: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(): LiveData<List<Tweet>> {
        return tweetList
    }

    fun getErrorState(): LiveData<Boolean> {
        return errorState
    }

    fun getTwitterFeed(searchTerm: String) {

        getTwitterDataInteractor.setSearchTerm(searchTerm).execute(object : Observer<List<Tweet>> {
            override fun onComplete() {
                println("complete")
            }

            override fun onSubscribe(d: Disposable) {
                getTwitterDataInteractor.addDisposable(d)
            }

            override fun onNext(t: List<Tweet>) {
                tweetList.postValue(t)
            }

            override fun onError(e: Throwable) {

                if (e is HttpException) {
                    println(e.message())
                    println(e.code())
                }

                if (e is SocketTimeoutException) {

                }

                errorState.postValue(true)
                println(e.message)
                println(e.cause)
                println(e.localizedMessage)
                e.printStackTrace()
            }
        })

    }

    fun unsubscribe() {
        getTwitterDataInteractor.unsubscribe()
    }
}