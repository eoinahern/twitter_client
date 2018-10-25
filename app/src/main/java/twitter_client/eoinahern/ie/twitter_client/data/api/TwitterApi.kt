package twitter_client.eoinahern.ie.twitter_client.data.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface TwitterApi {

    @GET("filter.json")
    @Streaming
    fun getTweets(): Call<ResponseBody>
}