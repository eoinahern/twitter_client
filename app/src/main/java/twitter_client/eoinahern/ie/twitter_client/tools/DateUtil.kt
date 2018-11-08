package twitter_client.eoinahern.ie.twitter_client.tools

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import javax.inject.Inject

@PerScreen
class DateUtil @Inject constructor() {

    private val zoneId: ZoneId by lazy { ZoneId.systemDefault() }

    fun getNowDateString(): String {
        return LocalDateTime.now(zoneId).toString()
    }

    fun checkIsDataStale(timeSaved: LocalDateTime, minutes: Long): Boolean {

        var nowTime = LocalDateTime.now(zoneId)
        var timeSavePlusTTL = timeSaved.plusMinutes(minutes)

        return nowTime.isAfter(timeSavePlusTTL)
    }
}