package twitter_client.eoinahern.ie.twitter_client.data.api

import com.google.common.escape.Escaper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import okio.Buffer
import android.util.Base64


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
    private val SIGNING_ALGO = "HmacSHA1"

    private val consumerKey: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_consumer_key
    private val consumerSecret = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_consumer_secret
    private val accessToken: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_access_token
    private val accessSecret: String = twitter_client.eoinahern.ie.twitter_client.BuildConfig.oauth_access_secret

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(signRequest(chain.request()))
    }

    private fun signRequest(request: Request): Request {

        val params: SortedMap<String, String> = TreeMap()

        val consumerKeyValue = urlEscaper.escape(consumerKey)
        val accessTokenValue = urlEscaper.escape(accessToken)
        val nonceValue = buildNonce()
        val timeStampValue = buildDateTime()

        params[OAUTH_CONSUMER_KEY] = consumerKeyValue
        params[OAUTH_ACCESS_TOKEN] = accessTokenValue
        params[OAUTH_NONCE] = nonceValue
        params[OAUTH_TIMESTAMP] = timeStampValue
        params[OAUTH_SIGNATURE_METHOD] = OAUTH_SIGNATURE_METHOD_VALUE
        params[OAUTH_VERSION] = OAUTH_VERSION_VALUE

        val url = request.url()
        for (i in 0 until url.querySize()) {
            params[urlEscaper.escape(url.queryParameterName(i))] = urlEscaper.escape(url.queryParameterValue(i))
        }

        val base = Buffer()
        val method = request.method()
        base.writeUtf8(method)
        base.writeUtf8("&")
        base.writeUtf8(urlEscaper.escape(request.url().newBuilder().query(null).build().toString()))
        base.writeUtf8("&")

        var first = true
        for ((key, value) in params) {
            if (!first) base.writeUtf8(urlEscaper.escape("&"))
            first = false
            base.writeUtf8(urlEscaper.escape(key))
            base.writeUtf8(urlEscaper.escape("="))
            base.writeUtf8(urlEscaper.escape(value))
        }

        val signingKey = urlEscaper.escape(consumerSecret).plus("&").plus(urlEscaper.escape(accessSecret))

        val keySpec = SecretKeySpec(signingKey.toByteArray(), SIGNING_ALGO)
        val mac: Mac
        try {
            mac = Mac.getInstance(SIGNING_ALGO)
            mac.init(keySpec)
        } catch (e: Exception) {
            throw Exception(e)
        }

        val result = mac.doFinal(base.readByteArray())
        val signature = Base64.encodeToString(result, Base64.NO_WRAP)


        val authStr = "OAuth ".plus(OAUTH_CONSUMER_KEY).plus("=\"$consumerKeyValue\", ")
            .plus(OAUTH_ACCESS_TOKEN).plus("=\"$accessTokenValue\", ")
            .plus(OAUTH_SIGNATURE_METHOD).plus("=\"$OAUTH_SIGNATURE_METHOD_VALUE\", ")
            .plus(OAUTH_TIMESTAMP).plus("=\"$timeStampValue\", ")
            .plus(OAUTH_NONCE).plus("=\"$nonceValue\", ")
            .plus(OAUTH_VERSION).plus("=\"$OAUTH_VERSION_VALUE\", ")
            .plus(OAUTH_SIGNATURE).plus("=\"${urlEscaper.escape(signature)}\"")


        println(authStr)
        return request.newBuilder().addHeader("Authorization", authStr).build()

    }

    private fun buildDateTime() = (System.currentTimeMillis() / 1000L).toString()

    private fun buildNonce(): String = UUID.randomUUID().toString().replace("-", "").substring(0, 32)

}