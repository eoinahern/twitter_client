package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import javax.inject.Inject

@PerScreen
class DeleteAllTweetsInteractor @Inject constructor(private val tweetsDao: TweetDao) : BaseInteractor<Unit>() {

    override fun buildObservable(): Observable<Unit> {
        return Observable.fromCallable {
            tweetsDao.deleteAll()
        }
    }
}