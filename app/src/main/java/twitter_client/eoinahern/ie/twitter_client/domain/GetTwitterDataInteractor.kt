package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import javax.inject.Inject

@PerScreen
class GetTwitterDataInteractor @Inject constructor(private val twitterApi: TwitterApi) : BaseInteractor<String>() {

    override fun buildObservable(): Observable<String> {

        return Observable.fromCallable {
            val call = twitterApi.getTweets("eoin")
             call.execute().body()?.string()
        }
    }
}