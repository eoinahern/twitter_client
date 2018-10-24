package twitter_client.eoinahern.ie.twitter_client.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Tweet(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val message: String,
    var datetime: String = ""
)

fun Tweet.setDateTime(dateTimeIn: String) {
    this.datetime = dateTimeIn
}