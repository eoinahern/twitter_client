package twitter_client.eoinahern.ie.twitter_client.tools.task

import android.os.Handler
import javax.inject.Inject
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed.TwitterFeedActivityCallback

@PerScreen
class RepeatingTaskExecutor @Inject constructor() {

    private val handler = Handler()
    private val intervalTime: Long = 30000
    private lateinit var runnableCode: Runnable
    private lateinit var twitterFeedActivityCallback: TwitterFeedActivityCallback

    fun setActivityCallback(callback: TwitterFeedActivityCallback) {
        twitterFeedActivityCallback = callback
    }

    fun startRepeatingTask() {

        runnableCode = object : Runnable {
            override fun run() {
                twitterFeedActivityCallback.checkTTLOnTweets()
                handler.postDelayed(this, intervalTime)
            }
        }

        handler.postDelayed(runnableCode, intervalTime)
    }

    fun clearTask() {
        handler.removeCallbacks(runnableCode)
    }
}