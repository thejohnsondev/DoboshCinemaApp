package com.johnsondev.doboshacademyapp.utilities

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException


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
    return when (countryName) {
        "United States of America" -> "USA"
        else -> countryName ?: ""
    }
}

fun calculateSpanCount(context: Context): Int {
    return if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        Constants.VERTICAL_SPAN_COUNT else Constants.HORIZONTAL_SPAN_COUNT
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun handleExceptions(e: Exception) {
    throw when (e) {
        is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
        else -> e
    }
}




