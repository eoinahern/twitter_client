package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import javax.inject.Inject

class TwitterFeedViewModel @Inject constructor(private val getTwitterDataInteractor: GetTwitterDataInteractor) :
    ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    private val errorStr: MutableLiveData<String> = MutableLiveData()

    fun getTwitterFeed() {

    }

    fun getDataAndInsert() {

    }


}