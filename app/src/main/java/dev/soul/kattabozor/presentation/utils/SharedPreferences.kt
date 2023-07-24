package dev.soul.kattabozor.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class SharedPreferences @Inject constructor(app: Context) {

    private val prefsName: String = "KattaBozor"
    private var prefs: SharedPreferences

    val version = "version"

    init {
        prefs = app.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        Log.d("TTT", prefsName)
    }

    fun save(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun get(key: String, defValue: Int) = prefs.getInt(key, defValue)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
