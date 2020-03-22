package com.fairideas.coronavirusinfo.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


/**
 * Created by illia-herman on 24.02.20.
 */
object PrefHelper {

    lateinit var preferences: SharedPreferences
    private val ACCESS_TOKEN = Pair("accessToken", "")
    private val IS_LOCATION_GRANTED = Pair("isLocationGranted", false)
    private val IS_STORAGE_GRANTED = Pair("isStorageGranted", false)
    private val IS_FIRST_INIT = Pair("isFirstInit", true)

    fun init(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN.first, ACCESS_TOKEN.second)
        set(value) = preferences.edit { it.putString(ACCESS_TOKEN.first, value) }

    var isLocationGranted: Boolean
        get() = preferences.getBoolean(
            IS_LOCATION_GRANTED.first,
            IS_LOCATION_GRANTED.second
        )
        set(value) = preferences.edit { it.putBoolean(IS_LOCATION_GRANTED.first, value) }

    var isStorageGranted: Boolean
        get() = preferences.getBoolean(
            IS_STORAGE_GRANTED.first,
            IS_STORAGE_GRANTED.second
        )
        set(value) = preferences.edit { it.putBoolean(IS_STORAGE_GRANTED.first, value) }

    var isFirstInit: Boolean
        get() = preferences.getBoolean(
            IS_FIRST_INIT.first,
            IS_FIRST_INIT.second
        )
        set(value) = preferences.edit { it.putBoolean(IS_FIRST_INIT.first, value) }
}