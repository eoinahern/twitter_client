package twitter_client.eoinahern.ie.twitter_client.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime


@Entity
data class Tweet(
    @SerializedName("text")
    val text: String,
    @Embedded
    @SerializedName("user")
    val user: User,
    var datetime: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

}

fun Tweet.getDateTime(): LocalDateTime {
    return LocalDateTime.parse(datetime)
}