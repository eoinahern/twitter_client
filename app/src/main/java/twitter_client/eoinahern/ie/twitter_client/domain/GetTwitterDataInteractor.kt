package twitter_client.eoinahern.ie.twitter_client.domain

import io.reactivex.Observable
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet


class GetTwitterDataInteractor : BaseInteractor<List<Tweet>>() {

    override fun buildObservable(): Observable<List<Tweet>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}