package twitter_client.eoinahern.ie.twitter_client

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import twitter_client.eoinahern.ie.twitter_client.di.component.DaggerAppComponent
import twitter_client.eoinahern.ie.twitter_client.di.module.AppModule
import javax.inject.Inject

class TwitterApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}