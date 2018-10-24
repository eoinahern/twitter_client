package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Dao
interface TweetDao {

    @Insert
    fun insertTweets(tweetList: List<Tweet>)

    @Query("DELETE FROM Tweet WHERE datetime <= :timeToDelete")
    fun deleteWhere(timeToDelete: String)


}