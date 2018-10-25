package twitter_client.eoinahern.ie.twitter_client.data.api

import com.google.common.escape.Escaper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.ByteString
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject


class OauthInterceptor @Inject constructor(
    private val random: Random, private val urlEscaper: Escaper
) : Interceptor {

    private val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
    private val OAUTH_ACCESS_TOKEN = "oauth_token"
    private val OAUTH_NONCE = "oauth_nonce"
    private val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
    private val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
    private val OAUTH_TIMESTAMP = "oauth_timestamp"
    private val OAUTH_VERSION = "oauth_version"
    private val OAUTH_VERSION_VALUE = "1.0"
    private val OAUTH_SIGNATURE = "oauth_signature"

    private val consumerKeyValue: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_consumer_key
    private val consumerSecret = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_consumer_secret
    private val accessToken: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_access_token
    private val accessSecret: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_access_secret

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(signRequest(chain.request()))
    }

    fun signRequest(request: Request): Request {


        var params: SortedMap<String, String> = TreeMap()

        params.put()
        params.put()
        params.put()
        params.put()
        params.put()

        var mac: Mac
        var spec: SecretKeySpec()


        val authStr = "OAuth ".plus(OAUTH_CONSUMER_KEY).plus("=\"$consumerKeyValue\",")
            .plus(OAUTH_ACCESS_TOKEN).plus("=\"$accessToken\",")
            .plus(OAUTH_SIGNATURE_METHOD).plus("=\"$OAUTH_SIGNATURE_METHOD_VALUE\",")
            .plus(OAUTH_VERSION).plus("=\"$OAUTH_VERSION_VALUE\",")
            .plus(OAUTH_TIMESTAMP).plus("=\"${buildDateTime()}\",")
            .plus(OAUTH_NONCE).plus("=\"${buildNonce()}\",")
            .plus(OAUTH_SIGNATURE).plus("=\"${buildSignature(request)}\"")

        return request.newBuilder()
            .addHeader("Authorization", authStr)
            .build()

    }


    // return oauth signature
    private fun buildSignature(request: Request): String {


        val str = request.method()



        return str
    }

    private fun buildDateTime() = (System.currentTimeMillis() / 1000).toString()


    private fun buildNonce(): String {

        val nonce = ByteArray(32)
        random.nextBytes(nonce)
        return ByteString.of(nonce, 0, nonce.size).base64().replace("\\W", "")
    }
}