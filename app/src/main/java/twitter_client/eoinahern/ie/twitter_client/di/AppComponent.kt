package twitter_client.eoinahern.ie.twitter_client.di

import twitter_client.eoinahern.ie.twitter_client.TwitterApp


interface AppComponent {

    fun inject(app: TwitterApp)
}