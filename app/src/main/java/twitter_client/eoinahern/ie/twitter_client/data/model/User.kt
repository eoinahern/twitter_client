package twitter_client.eoinahern.ie.twitter_client.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String)