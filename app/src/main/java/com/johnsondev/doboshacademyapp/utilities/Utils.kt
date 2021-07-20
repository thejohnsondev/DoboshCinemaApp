package com.johnsondev.doboshacademyapp.utilities

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    return if (lastUpdateTimeRaw?.length != 0) {
        lastUpdateTimeRaw!!.substring(4, 19)
    } else {
        ""
    }
}

fun animateView(
    view: View, property: String, animDuration: Long, vararg values: Float
): Animator {
    var valueAnimator: Animator? = null
    when (view) {
        is TextView -> {
            if (property == "maxLines") {
                val specValues = values.map { it.toInt() }.toIntArray()
                valueAnimator = ValueAnimator.ofInt(*specValues).apply {
                    addUpdateListener {
                        val animatedValue = it.animatedValue as Int
                        view.maxLines = animatedValue
                    }
                    duration = animDuration
                }
            }
        }
        is ImageView -> {
            if (property == "alpha") {
                valueAnimator = ValueAnimator.ofFloat(*values).apply {
                    addUpdateListener {
                        val animatedValue = it.animatedValue as Float
                        view.alpha = animatedValue
                    }
                    duration = animDuration
                }
            }
        }
    }
    return valueAnimator ?: ValueAnimator()
}