package twitter_client.eoinahern.ie.twitter_client.tools

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import javax.inject.Inject

@PerScreen
class DateUtil @Inject constructor() {

    private val zoneId: ZoneId by lazy { ZoneId.systemDefault() }

    fun getLinuxTimeNow() : Long {
        val time = LocalDateTime.now(zoneId).toEpochSecond(ZoneOffset.UTC)
        println(time.toString())
        return time
    }
}