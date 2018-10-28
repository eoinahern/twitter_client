package twitter_client.eoinahern.ie.twitter_client.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed.TwitterFeedActivity

@Module
abstract class BuilderModule {

    @PerScreen
    @ContributesAndroidInjector
    abstract fun getTweetActivity(): TwitterFeedActivity


}