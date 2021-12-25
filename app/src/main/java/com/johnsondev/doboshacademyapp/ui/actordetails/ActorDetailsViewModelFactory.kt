package com.johnsondev.doboshacademyapp.ui.actordetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johnsondev.doboshacademyapp.data.repositories.actors.ActorsRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.SUPPRESS_UNCHECKED_CAST

@Suppress(SUPPRESS_UNCHECKED_CAST)
class ActorDetailsViewModelFactory(
    val application: Application,
    private val actorsRepository: ActorsRepository
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActorDetailsViewModel(application, actorsRepository) as T
    }
}