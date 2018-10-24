package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet


class TwitterFeedViewModel : ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    private val errorStr: MutableLiveData<String> = MutableLiveData()

    fun getTwitterFeed() {

    }


}