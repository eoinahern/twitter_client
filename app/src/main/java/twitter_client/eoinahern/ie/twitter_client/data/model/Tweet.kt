package twitter_client.eoinahern.ie.twitter_client.data.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime


data class Tweet(
    @SerializedName("id_str")
    val id_str: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: User,
    var datetime: String = ""
)

fun Tweet.getDateTime(): LocalDateTime {
    return LocalDateTime.parse(datetime)
}