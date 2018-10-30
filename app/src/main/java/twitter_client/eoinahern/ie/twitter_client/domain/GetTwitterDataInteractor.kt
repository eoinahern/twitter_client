package twitter_client.eoinahern.ie.twitter_client.domain

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import io.reactivex.Observable
import okio.BufferedSource
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.data.model.User
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@PerScreen
class GetTwitterDataInteractor @Inject constructor(private val twitterApi: TwitterApi) : BaseInteractor<List<Tweet>>() {

    private var searchTerm = "twitter"
    private val nameKey = "name"
    private val textKey = "text"
    private val userKey = "user"

    fun setSearchTerm(searchTerm: String): GetTwitterDataInteractor {
        this.searchTerm = searchTerm
        return this
    }

    override fun buildObservable(): Observable<List<Tweet>> {
        return twitterApi.getTweets(searchTerm).flatMap {
            events(it.source())
        }
    }

    private fun events(buffSource: BufferedSource): Observable<List<Tweet>> {

        return Observable.create<List<Tweet>> { subscriber ->
            try {

                val jsonReader = JsonReader(InputStreamReader(buffSource.inputStream(), "UTF-8"))
                jsonReader.isLenient = true
                var tweetList: MutableList<Tweet> = mutableListOf()
                var textIn = ""
                var userIn = User("")

                while (!buffSource.exhausted()) {

                    if (jsonReader.peek() == JsonToken.BEGIN_OBJECT) {
                        jsonReader.beginObject()
                    } else {
                        println(jsonReader.peek().toString())
                    }

                    while (jsonReader.hasNext()) {

                        val name = jsonReader.nextName()

                        when (name) {
                            textKey -> textIn = jsonReader.nextString()
                            userKey -> userIn = createUser(jsonReader)
                            else -> jsonReader.skipValue()
                        }
                    }

                    if (jsonReader.peek() == JsonToken.END_OBJECT) {
                        jsonReader.endObject()
                    }

                    val tweet = Tweet(text = textIn, user = userIn)
                    tweetList.add(tweet)

                    if (tweetList.size == 15) {
                        subscriber.onNext(tweetList)
                        tweetList.clear()
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                subscriber.onError(e)
            }

            subscriber.onComplete()
        }
    }

    private fun createUser(jsonReader: JsonReader): User {

        jsonReader.beginObject()

        var nameValue = ""

        while (jsonReader.hasNext()) {

            val name = jsonReader.nextName()

            when (name) {
                nameKey -> nameValue = jsonReader.nextString()
                else -> jsonReader.skipValue()
            }
        }

        jsonReader.endObject()
        return User(nameValue)
    }
}