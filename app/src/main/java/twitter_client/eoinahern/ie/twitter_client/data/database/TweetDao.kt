package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Dao
interface TweetDao {

    @Insert
    fun insertTweets(tweetList: List<Tweet>)

    @Insert
    fun insertTweet(tweet: Tweet)

    @Query("DELETE FROM Tweet WHERE datetime <= :timeToDelete")
    fun deleteWhere(timeToDelete: String)

    @Query("SELECT * FROM Tweet")
    fun getAll(): LiveData<List<Tweet>>

}