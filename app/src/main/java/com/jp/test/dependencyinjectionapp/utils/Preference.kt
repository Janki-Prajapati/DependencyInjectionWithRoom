package com.jp.test.dependencyinjectionapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.jp.test.dependencyinjectionapp.MyApplication

object Preference {
    private val PREF_KEY = "Preference"
    const val PREF_IS_LOGGED_IN = "pref_is_logged_in"
    const val PREF_USER_ID = "pref_user_id"

    private var sSharedPreferences: SharedPreferences? = null

    private val instance: SharedPreferences?
        get() {
            try {
                if (sSharedPreferences == null) { //if there is no instance available... create new one
                    synchronized(SharedPreferences::class.java) {
                        if (sSharedPreferences == null) sSharedPreferences =
                            MyApplication.context?.getSharedPreferences(
                                PREF_KEY, Context.MODE_PRIVATE
                            )
                    }
                }
            } catch (e: Exception) {
                println(e)
            }
            return sSharedPreferences
        }

    fun savePreferencesBoolean(
        key: String,
        value: Boolean
    ) {
        try {
            println("SavePreferencesBoolean call")
            val mEditor = instance?.edit()
            mEditor?.putBoolean(key, value)
            mEditor?.commit()
        } catch (e: Exception) {
            println(e)
        }
    }

    fun getPreferencesBooleanWithDefault(
        key: String,
        defaultVal: Boolean
    ): Boolean {
        println("getPreferencesBooleanWithDefault call")
        var mSavedPref = defaultVal
        try {
            mSavedPref = instance?.getBoolean(key, defaultVal) == true
        } catch (e: Exception) {
            println(e)
        }
        return mSavedPref
    }

    fun savePreferencesInt(
        key: String,
        value: Int
    ) {
        try {
            println("SavePreferencesBoolean call")
            val mEditor = instance?.edit()
            mEditor?.putInt(key, value)
            mEditor?.commit()
        } catch (e: Exception) {
            println(e)
        }
    }

    fun getPreferencesIntWithDefault(
        key: String,
        defaultVal: Int
    ): Int {
        println("getPreferencesBooleanWithDefault call")
        var mSavedPref = defaultVal
        try {
            mSavedPref = instance?.getInt(key, defaultVal) ?: -1
        } catch (e: Exception) {
            println(e)
        }
        return mSavedPref
    }

}