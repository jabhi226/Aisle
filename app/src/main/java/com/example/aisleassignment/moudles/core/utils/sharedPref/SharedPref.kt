package com.example.aisleassignment.moudles.core.utils.sharedPref

import android.app.Activity
import android.content.Context

object SharedPref {

    private const val TAG = "AisleCacheData"

    fun getString(context: Context, key: String?): String {
        var returnString = ""
        val sp = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
        returnString = sp.getString(key, "").toString()
        return returnString
    }

    fun setString(context: Context, key: String?, value: String?) {
        val sharedPreferences = context.getSharedPreferences(TAG, Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}