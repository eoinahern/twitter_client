package twitter_client.eoinahern.ie.twitter_client.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import twitter_client.eoinahern.ie.twitter_client.data.api.OauthInterceptor
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.tools.BASE_URL


@Module
class NetworkModule {

    @Provides
    fun getInterceptor(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(OauthInterceptor())
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