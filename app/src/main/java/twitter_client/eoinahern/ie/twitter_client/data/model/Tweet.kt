package twitter_client.eoinahern.ie.twitter_client.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Tweet(
    @PrimaryKey
    @SerializedName("id_str")
    val id_str: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    @Embedded
    val user: User,
    var datetime: Long
)