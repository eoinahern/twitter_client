package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import twitter_client.eoinahern.ie.twitter_client.R

class TwitterFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_feed)

        println(twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_consumer_key)
        println(twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_token)
    }
}
