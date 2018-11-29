package twitter_client.eoinahern.ie.twitter_client.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet

@Database(entities = [Tweet::class], version = 1)
abstract class TwitterDatabase : RoomDatabase() {

    abstract fun getTweetDao(): TweetDao
}