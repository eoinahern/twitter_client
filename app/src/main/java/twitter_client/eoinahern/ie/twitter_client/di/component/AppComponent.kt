package twitter_client.eoinahern.ie.twitter_client.di.component

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import twitter_client.eoinahern.ie.twitter_client.TwitterApp
import twitter_client.eoinahern.ie.twitter_client.di.module.AppModule
import twitter_client.eoinahern.ie.twitter_client.di.module.BuilderModule
import twitter_client.eoinahern.ie.twitter_client.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, BuilderModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(app: TwitterApp)
}