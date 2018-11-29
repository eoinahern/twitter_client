package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Dao
interface TweetDao {

    @Query("SELECT * FROM Tweet")
    fun getTweets(): List<Tweet>

    @Insert
    fun addTweets(tweets: List<Tweet>)


}