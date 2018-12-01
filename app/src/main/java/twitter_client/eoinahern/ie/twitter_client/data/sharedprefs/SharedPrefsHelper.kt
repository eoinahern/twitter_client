package twitter_client.eoinahern.ie.twitter_client.data.sharedprefs

import android.content.SharedPreferences

class SharedPrefsHelper constructor(
    private val sharedPrefs: SharedPreferences,
    private val sharedPrefsEdit: SharedPreferences.Editor
) {

    fun getLong(key: String): Long {
        return sharedPrefs.getLong(key, 1L)
    }

    fun saveLong(key: String, value: Long) {
        sharedPrefsEdit.putLong(key, value).commit()
    }
}