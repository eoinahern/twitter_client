package twitter_client.eoinahern.ie.twitter_client.di.module

import com.google.common.escape.Escaper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import twitter_client.eoinahern.ie.twitter_client.data.api.OauthInterceptor
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.tools.BASE_URL
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getClient(random: Random, escaper: Escaper): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(OauthInterceptor(random, escaper))
        .build()

    @Singleton
    @Provides
    fun getApi(random: Random, escaper: Escaper): TwitterApi {

        var client = OkHttpClient.Builder().addInterceptor(OauthInterceptor(random, escaper)).build()

        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL).build()
            .create(TwitterApi::class.java)
    }
}