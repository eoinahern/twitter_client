package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_twitter_feed.view.*
import retrofit2.HttpException
import twitter_client.eoinahern.ie.twitter_client.R
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.data.model.getDateTime
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import twitter_client.eoinahern.ie.twitter_client.tools.DateUtil
import twitter_client.eoinahern.ie.twitter_client.tools.TWEET_TTL_KEY
import twitter_client.eoinahern.ie.twitter_client.tools.resources.ResourceProvider
import java.net.SocketTimeoutException
import javax.inject.Inject

@PerScreen
class TwitterFeedViewModel @Inject constructor(
    private val getTwitterDataInteractor: GetTwitterDataInteractor,
    private val dateUtil: DateUtil,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val resourceProvider: ResourceProvider
) :
    ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    private val errorState: MutableLiveData<String> = MutableLiveData()
    private val dataList: MutableList<Tweet> = mutableListOf()

    fun getData(): LiveData<List<Tweet>> = tweetList

    fun getErrorState(): LiveData<String> {
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
                dataList.let {
                    it.addAll(t)
                    tweetList.postValue(it)
                }
            }

            override fun onError(e: Throwable) {

                if (e is SocketTimeoutException) {
                    errorState.postValue(resourceProvider.getString(R.string.no_data_found))
                    return
                }

                errorState.postValue(resourceProvider.getString(R.string.error_loading))
            }
        })
    }

    fun clearTweetList() {
        dataList.clear()
    }

    fun setTTLTime(ttl: Long) {
        sharedPrefsHelper.saveLong(TWEET_TTL_KEY, ttl)
    }

    /**
     * could potentially use ReactiveStreams on DB
     * with linux timestamp. run DELETE query against
     * current timestamp
     * TODO: wrap in interactor
     */


    fun delete() {
        Observable.fromCallable {

            val ttl = sharedPrefsHelper.getLong(TWEET_TTL_KEY)
            dataList.removeAll {
                dateUtil.checkIsDataStale(it.getDateTime(), ttl)
            }
            dataList
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tweetList.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

    fun unsubscribe() {
        getTwitterDataInteractor.unsubscribe()
    }
}