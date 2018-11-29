package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import twitter_client.eoinahern.ie.twitter_client.R
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
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
    private val tweetDao: TweetDao,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val resourceProvider: ResourceProvider
) :
    ViewModel() {

    private val errorState: MutableLiveData<String> = MutableLiveData()


    fun getCachedData(): LiveData<List<Tweet>> {
        return LiveDataReactiveStreams.fromPublisher(tweetDao.getAllTweets())
    }

    fun getErrorState(): LiveData<String> {
        return errorState
    }

    fun getTwitterFeed(searchTerm: String) {
        getTwitterDataInteractor.setSearchTerm(searchTerm).execute(object : Observer<Unit> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                getTwitterDataInteractor.addDisposable(d)
            }

            override fun onNext(t: Unit) {
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

    fun setTTLTime(ttl: Long) {
        sharedPrefsHelper.saveLong(TWEET_TTL_KEY, ttl)
    }

    /**
     * could potentially use ReactiveStreams on DB
     * with linux timestamp. run DELETE query against
     * current timestamp
     *
     */

    fun delete() {

        Observable.fromCallable {
            tweetDao.deleteSubsetTweets(sharedPrefsHelper.getLong(TWEET_TTL_KEY), dateUtil.getLinuxTimeNow())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


    private fun deleteAll() {

        Observable.fromCallable {
            tweetDao.deleteAll()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun unsubscribe() {
        deleteAll()
        getTwitterDataInteractor.unsubscribe()
    }
}