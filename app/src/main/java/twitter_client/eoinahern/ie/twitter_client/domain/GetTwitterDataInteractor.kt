package twitter_client.eoinahern.ie.twitter_client.domain

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import io.reactivex.Observable
import okio.BufferedSource
import twitter_client.eoinahern.ie.twitter_client.data.api.TwitterApi
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.data.model.User
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.tools.DEFAULT_SEARCH
import twitter_client.eoinahern.ie.twitter_client.tools.DateUtil
import java.io.InputStreamReader
import javax.inject.Inject

@PerScreen
class GetTwitterDataInteractor @Inject constructor(
    private val twitterApi: TwitterApi, private val dateUtil: DateUtil
) : BaseInteractor<List<Tweet>>() {

    private var searchTerm = "twitter"
    private val nameKey = "name"
    private val idKey = "id_str"
    private val textKey = "text"
    private val userKey = "user"

    fun setSearchTerm(searchTerm: String): GetTwitterDataInteractor {
        this.searchTerm = searchTerm
        return this
    }

    override fun buildObservable(): Observable<List<Tweet>> {

        if (searchTerm.isBlank())
            searchTerm = DEFAULT_SEARCH

        return twitterApi.getTweets(searchTerm).flatMap {
            events(it.source())
        }
    }

    private fun events(buffSource: BufferedSource): Observable<List<Tweet>> {

        return Observable.create<Tweet> { subscriber ->
            try {

                val jsonReader = JsonReader(InputStreamReader(buffSource.inputStream(), "UTF-8"))
                jsonReader.isLenient = true
                var textIn = ""
                var idStrIn = ""
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
                            idKey -> idStrIn = jsonReader.nextString()
                            textKey -> textIn = jsonReader.nextString()
                            userKey -> userIn = createUser(jsonReader)
                            else -> jsonReader.skipValue()
                        }
                    }

                    if (jsonReader.peek() == JsonToken.END_OBJECT) {
                        jsonReader.endObject()
                    }

                    val tweet =
                        Tweet(id_str = idStrIn, text = textIn, user = userIn, datetime = dateUtil.getNowDateString())
                    subscriber.onNext(tweet)

                }
            } catch (e: Throwable) {
                e.printStackTrace()

                if (!subscriber.isDisposed)
                    subscriber.onError(e)
            }

            if (!subscriber.isDisposed)
                subscriber.onComplete()
        }.buffer(5)
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