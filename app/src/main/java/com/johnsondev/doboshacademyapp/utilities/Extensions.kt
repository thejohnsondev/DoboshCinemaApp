package com.johnsondev.doboshacademyapp.utilities


import android.widget.Toast
import androidx.fragment.app.Fragment




fun Fragment.showMessage(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}