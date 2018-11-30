package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.tools.DateUtil
import twitter_client.eoinahern.ie.twitter_client.tools.TWEET_TTL_KEY
import javax.inject.Inject

@PerScreen
class DeleteExpiredTweetsInteractor @Inject constructor(
    private val tweetDao: TweetDao,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val dateUtil: DateUtil
) : BaseInteractor<Unit>() {

    override fun buildObservable(): Observable<Unit> {

        return Observable.fromCallable {
            tweetDao.deleteSubsetTweets(
                sharedPrefsHelper.getLong(TWEET_TTL_KEY),
                dateUtil.getLinuxTimeNow()
            )
        }
    }
}