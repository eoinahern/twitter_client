package twitter_client.eoinahern.ie.twitter_client.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Database
import androidx.room.Room
import com.google.common.escape.Escaper
import com.google.common.net.UrlEscapers
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import twitter_client.eoinahern.ie.twitter_client.TwitterApp
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.database.TwitterDatabase
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.tools.TWITTER_DB_KEY
import java.util.*
import javax.inject.Singleton

@Module
class AppModule constructor(private val app: TwitterApp) {

    @Singleton
    @Provides
    fun getContext(): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun getRandom(): Random {
        return Random()
    }

    @Singleton
    @Provides
    fun getUrlEscaper(): Escaper {
        return UrlEscapers.urlFormParameterEscaper()
    }

    @Singleton
    @Provides
    fun getGson(): Gson = Gson()

    @Singleton
    @Provides
    fun getSharedPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun getSharedPrefsEditor(sharedPrefereces: SharedPreferences): SharedPreferences.Editor {
        return sharedPrefereces.edit()
    }

    @Singleton
    @Provides
    fun getSharedPrefsHelper(
        sharedPrefs: SharedPreferences,
        sharedPrefsEdit: SharedPreferences.Editor
    ): SharedPrefsHelper {
        return SharedPrefsHelper(sharedPrefs, sharedPrefsEdit)
    }

    @Singleton
    @Provides
    fun getDatabase(context: Context): TwitterDatabase {
        return Room.databaseBuilder(context, TwitterDatabase::class.java, TWITTER_DB_KEY).build()
    }

    @Singleton
    @Provides
    fun getDao(twitterDatabase: TwitterDatabase): TweetDao {
        return twitterDatabase.getTweetDao()
    }
}