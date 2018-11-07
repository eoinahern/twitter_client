package twitter_client.eoinahern.ie.twitter_client.data.sharedprefs

import android.content.SharedPreferences

class SharedPrefsHelper constructor(
    private val sharedPrefs: SharedPreferences,
    private val sharedPrefsEdit: SharedPreferences.Editor
) {

    fun getString(key: String): String? {
        return sharedPrefs.getString(key, "")
    }

    fun saveString(key: String, value: String) {
        sharedPrefsEdit.putString(key, value).commit()
    }

    fun getLong(key: String): Long? {
        return sharedPrefs.getLong(key, 0L)
    }

    fun saveLong(key: String, value: Long) {
        sharedPrefsEdit.putLong(key, value).commit()
    }
}