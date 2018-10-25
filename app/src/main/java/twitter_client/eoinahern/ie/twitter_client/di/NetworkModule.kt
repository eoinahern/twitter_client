package twitter_client.eoinahern.ie.twitter_client.di

import com.google.common.escape.Escaper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import twitter_client.eoinahern.ie.twitter_client.data.api.OauthInterceptor
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.tools.BASE_URL
import java.util.*

@Module
class NetworkModule {

    @Provides
    fun getClient(random: Random, escaper: Escaper): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(OauthInterceptor(random, escaper))
        .build()

    @Provides
    fun getApi(client: OkHttpClient): TwitterApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(TwitterApi::class.java)
    }
}