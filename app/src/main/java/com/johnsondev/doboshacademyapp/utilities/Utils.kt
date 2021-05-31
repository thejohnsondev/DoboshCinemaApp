package com.johnsondev.doboshacademyapp.utilities

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*


fun saveUpdateTime(context: Context) {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val prefEditor = sharedPref.edit()

    val currentTime = Calendar.getInstance().time.toString()

    prefEditor.putString(Constants.PREF_UPDATE_TIME, currentTime)
    prefEditor.apply()
}

fun getUpdateTime(context: Context): String {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val lastUpdateTimeRaw = sharedPref.getString(Constants.PREF_UPDATE_TIME, "")
    return "${lastUpdateTimeRaw?.substring(4, 19)}"
}