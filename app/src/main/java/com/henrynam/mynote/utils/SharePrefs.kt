package com.henrynam.mynote.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharePrefs(context: Context) {
    private var mInstance: SharePrefs? = null

    companion object {
        lateinit var mSharedPreferences: SharedPreferences
    }

    init {
        mSharedPreferences = context.getSharedPreferences("shared pref", Context.MODE_PRIVATE)
    }

    fun getInstance(): SharePrefs {
        if (mInstance == null) {
            mInstance = this
        }
        return mInstance as SharePrefs
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Boolean::class.java -> java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(key, false)) as T
            Float::class.java -> java.lang.Float.valueOf(mSharedPreferences.getFloat(key, 0f)) as T
            Int::class.java -> Integer.valueOf(mSharedPreferences.getInt(key, 0)) as T
            Long::class.java -> java.lang.Long.valueOf(mSharedPreferences.getLong(key, 0)) as T
            else -> null
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}