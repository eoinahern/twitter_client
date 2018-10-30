package twitter_client.eoinahern.ie.twitter_client.di.module

import android.content.Context
import com.google.common.escape.Escaper
import com.google.common.net.UrlEscapers
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import twitter_client.eoinahern.ie.twitter_client.TwitterApp
import java.util.*
import javax.inject.Singleton

@Module
class AppModule constructor(private val app: TwitterApp) {

    @Singleton
    @Provides
    fun getContext(): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun getRandom(): Random {
        return Random()
    }

    @Singleton
    @Provides
    fun getUrlEscaper(): Escaper {
        return UrlEscapers.urlFormParameterEscaper()
    }

    @Singleton
    @Provides
    fun getGson(): Gson = Gson()

}