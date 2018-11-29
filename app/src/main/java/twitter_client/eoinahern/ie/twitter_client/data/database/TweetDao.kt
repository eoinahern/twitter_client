package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.room.*
import io.reactivex.Flowable
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Dao
interface TweetDao {

    @Query("SELECT * FROM Tweet")
    fun getAllTweets(): Flowable<List<Tweet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTweets(tweets: List<Tweet>)

    @Query("DELETE FROM Tweet")
    fun deleteAll()
}