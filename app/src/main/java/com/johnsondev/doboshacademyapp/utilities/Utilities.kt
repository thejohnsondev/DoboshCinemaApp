package com.johnsondev.doboshacademyapp.utilities

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.preference.PreferenceManager
import java.util.*


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
        else -> {
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

fun timeToHFromMin(timeInput: Int): Array<Int> {

    var timeInMin = timeInput
    var h = 0
    var min = 0

    while (timeInMin > 60) {
        h++
        timeInMin -= 60
        min = timeInMin
    }

    return arrayOf(h, min)

}

fun revertDate(inputDate: String): String {
    if (inputDate == "") return inputDate
    val components: List<String> = inputDate.split("-")
    return "${components[2]}.${components[1]}.${components[0]}"
}

fun checkCountryName(countryName: String?): String {
    return when(countryName){
        "United States of America" -> "USA"
        else -> countryName ?: ""
    }
}




