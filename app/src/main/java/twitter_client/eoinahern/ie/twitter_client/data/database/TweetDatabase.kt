package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.room.Database
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Database(entities = [Tweet::class], version = 1, exportSchema = false)
abstract class TweetDatabase {

    abstract fun getTweetDao(): TweetDao
}