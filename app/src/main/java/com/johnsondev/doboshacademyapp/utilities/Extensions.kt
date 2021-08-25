package com.johnsondev.doboshacademyapp.utilities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import com.johnsondev.doboshacademyapp.R

fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun Fragment.replaceFragment(fragment: Fragment) {

    this.parentFragmentManager.beginTransaction().setCustomAnimations(
        R.anim.slide_in,
        R.anim.fade_out,
        R.anim.fade_in,
        R.anim.slide_out
    )
        .addToBackStack(null)
        .replace(R.id.main_container, fragment)
        .commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, bundle: Bundle) {
    fragment.arguments = bundle
    supportFragmentManager.beginTransaction().apply {
        add(R.id.main_container, fragment)
        commit()
    }
}

fun Fragment.showMessage(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}