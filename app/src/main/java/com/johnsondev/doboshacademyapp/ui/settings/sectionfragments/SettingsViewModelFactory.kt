package com.johnsondev.doboshacademyapp.ui.settings.sectionfragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.ui.settings.SettingsViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants.SUPPRESS_UNCHECKED_CAST

@Suppress(SUPPRESS_UNCHECKED_CAST)
class SettingsViewModelFactory(
    val application: Application,
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(application) as T
    }
}