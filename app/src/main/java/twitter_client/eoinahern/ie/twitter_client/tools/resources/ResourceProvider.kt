package twitter_client.eoinahern.ie.twitter_client.tools.resources

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(private val context: Context) {
    fun getString(resId: Int): String = context.getString(resId)

}