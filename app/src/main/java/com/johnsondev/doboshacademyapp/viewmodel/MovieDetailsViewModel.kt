package com.johnsondev.doboshacademyapp.viewmodel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_TITLE
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_PATH
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_ALL_DAY
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_BEGIN_TIME
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_DESCRIPTION
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_END_TIME
import kotlinx.coroutines.launch
import java.util.*

class MovieDetailsViewModel : ViewModel() {

    private var _mutableActorList = MutableLiveData<List<Actor>>()
    val actorsList: LiveData<List<Actor>> get() = _mutableActorList

    fun getActorsForCurrentMovie() {
        viewModelScope.launch {
            try {
                _mutableActorList = ActorsRepository.getActorsForCurrentMovie()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadActorsForMovieById(movieId: Int) {
        viewModelScope.launch {
            ActorsRepository.loadActors(movieId)
        }
    }


    fun getMovieById(id: Int): Movie {
        return MoviesRepository.getMovieByIdFromDb(id)
    }

    fun callDatePicker(context: Context, date: Calendar, currentMovie: Movie) {

        var year: Int
        var month: Int
        var day: Int
        var hour: Int
        var minute: Int

        Calendar.getInstance().apply {
            year = get(Calendar.YEAR)
            month = get(Calendar.MONTH)
            day = get(Calendar.DAY_OF_MONTH)
            hour = get(Calendar.HOUR)
            minute = get(Calendar.MINUTE)
        }

        DatePickerDialog(
            context,
            { _, yearDate, monthDate, dayOfMonth ->
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        date.set(yearDate, monthDate, dayOfMonth, hourOfDay, minute)
                        saveToCalendar(context, date, currentMovie)
                    },
                    hour,
                    minute,
                    true
                ).show()
            },
            year,
            month,
            day
        ).show()
    }

    private fun saveToCalendar(context: Context, date: Calendar, currentMovie: Movie): Intent {
        val intent = Intent(Intent.ACTION_EDIT).apply {
            type = CALENDAR_PATH
            putExtra(CALENDAR_VAL_BEGIN_TIME, date.timeInMillis)
            putExtra(CALENDAR_VAL_ALL_DAY, false)
            putExtra(CALENDAR_VAL_END_TIME, date.timeInMillis + 60 * 60 * 1000)
            putExtra(
                CALENDAR_VAL_TITLE,
                "${App.getInstance().getString(R.string.watch_the_movie)} ${currentMovie.title}"
            )
            putExtra(
                CALENDAR_VAL_DESCRIPTION,
                "${context.getString(R.string.open_movie_details)}${context.getString(R.string.base_deep_link)}${currentMovie.id}"
            )
        }
        context.startActivity(intent)
        return intent
    }

}