package twitter_client.eoinahern.ie.twitter_client.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface TwitterApi {

    @GET("filter.json")
    @Streaming
    fun getTweets(@Query("track") track: String): Observable<ResponseBody>
}