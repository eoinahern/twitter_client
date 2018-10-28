package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.domain.BaseInteractor
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import javax.inject.Inject


@PerScreen
class TwitterFeedViewModel @Inject constructor(private val twitterApi: TwitterApi) :
    ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    private val errorStr: MutableLiveData<String> = MutableLiveData()
    private lateinit var disp: Disposable

    fun getData(): LiveData<String> {
        return errorStr
    }

    fun getTwitterFeed() {

        twitterApi.getTweets("twitter").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println(t.localizedMessage)
                println(t.message)
                println(t.cause)
                println(t.stackTrace)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                println(response.errorBody().toString())
                println(call.request().headers())
                println(response.message())
                println(response.isSuccessful)
                println(response.code())

                disp = Observable.fromCallable { response.body()?.string() }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        System.out.println(it)
                    }, {
                        it.printStackTrace()
                    })
            }
        })

        /* getTwitterDataInteractor.execute(object : Observer<String> {
            override fun onComplete() {
                println("complete")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: String) {
                println(t)
                errorStr.postValue(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                println(e.message)
                println(e.localizedMessage)
                println(e.cause)
            }
        })*/


    }

    fun onUnsunscribe() {
        disp.dispose()
    }

}