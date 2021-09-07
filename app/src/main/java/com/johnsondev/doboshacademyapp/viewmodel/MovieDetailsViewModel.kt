package com.johnsondev.doboshacademyapp.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.CrewMember
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.models.MovieDetails
import com.johnsondev.doboshacademyapp.data.network.api.MovieApi
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.data.repositories.ActorsRepository
import com.johnsondev.doboshacademyapp.data.repositories.MoviesRepository
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_TITLE
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_PATH
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_ALL_DAY
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_BEGIN_TIME
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_DESCRIPTION
import com.johnsondev.doboshacademyapp.utilities.Constants.CALENDAR_VAL_END_TIME
import com.johnsondev.doboshacademyapp.utilities.InternetConnectionManager
import com.johnsondev.doboshacademyapp.utilities.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

class MovieDetailsViewModel(application: Application) : BaseViewModel(application) {

    private var _actorList = MutableLiveData<List<Actor>>()
    private var _crewList = MutableLiveData<List<CrewMember>>()

    private var _actorDetails = MutableLiveData<ActorDetailsDto>()
    private var _actorMovieCredits = MutableLiveData<List<Movie>>()
    private var _actorImages = MutableLiveData<List<ActorImageProfileDto>>()

    private var _averageColorBody = MutableLiveData<Int>()
    private var _averageColorText = MutableLiveData<Int>()

    private var _currentMovie = MutableLiveData<MovieDetails>()
    private var _movieVideos = MutableLiveData<List<MovieVideoDto>>()
    private var _movieImages = MutableLiveData<Map<String, List<MovieImageDto>>>()
    private var _movieRecommendations = MutableLiveData<List<Movie>>()


    fun loadMovieFromNetById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            MoviesRepository.loadMovieById(id)
            mutableError.value = null
        }
    }

    fun loadCastForMovieById(movieId: Int) {
        viewModelScope.launch(exceptionHandler()) {
            ActorsRepository.loadCast(movieId)
            mutableError.value = null
        }
    }

    fun loadActorDetailsById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            ActorsRepository.loadActorDetailsById(id)
            mutableError.value = null
        }
    }

    fun loadMovieVideosById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            MoviesRepository.loadMovieVideosById(id)
            mutableError.value = null
        }
    }

    fun loadMovieImagesById(id: Int) {
        viewModelScope.launch(exceptionHandler()) {
            MoviesRepository.loadMovieImages(id)
            mutableError.value = null
        }
    }

    fun loadRecommendationsByMovieId(id: Int){
        viewModelScope.launch {
            MoviesRepository.loadRecommendationsByMovieId(id)
            mutableError.value = null
        }
    }

    fun getRecommendations(): LiveData<List<Movie>>{
        _movieRecommendations = MoviesRepository.getRecommendations()
        return _movieRecommendations
    }

    fun getMovieImagesForCurrentMovie(): LiveData<Map<String, List<MovieImageDto>>> {
        _movieImages = MoviesRepository.getMovieImages()
        return _movieImages
    }


    fun getCurrentMovieFromNet(): LiveData<MovieDetails> {
        _currentMovie = MoviesRepository.getCurrentMovie()
        return _currentMovie
    }


    fun getActorsForCurrentMovie(): LiveData<List<Actor>> {
        _actorList = ActorsRepository.getActorsForCurrentMovie()
        return _actorList
    }

    fun getCrewForCurrentMovie(): LiveData<List<CrewMember>> {
        _crewList = ActorsRepository.getCrewForCurrentMovie()
        return _crewList
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


    fun getActorDetails(): LiveData<ActorDetailsDto> {
        _actorDetails = ActorsRepository.getActorDetails()
        return _actorDetails
    }

    fun getActorMovieCredits(): LiveData<List<Movie>> {
        _actorMovieCredits = ActorsRepository.getActorMovieCredits()
        return _actorMovieCredits
    }

    fun getActorImages(): LiveData<List<ActorImageProfileDto>> {
        _actorImages = ActorsRepository.getActorImages()
        return _actorImages
    }

    fun clearActorDetails() {
        _actorImages.value = emptyList()
        _actorMovieCredits.value = emptyList()
        _actorDetails.value = ActorDetailsDto()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateAverageColor(imageUrl: String, context: Context) {
        viewModelScope.launch {
            Glide.with(context).asBitmap().load(imageUrl).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val palette = Palette.from(resource).generate()
                    MoviesRepository.setAverageColor(
                        palette.getDarkMutedColor(Color.BLACK),
                        palette.getLightMutedColor(Color.GRAY)
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        }


    }

    fun getAverageColorBody(): LiveData<Int> {
        _averageColorBody = MoviesRepository.getAverageColorBody()
        return _averageColorBody
    }

    fun getAverageColorText(): LiveData<Int> {
        _averageColorText = MoviesRepository.getAverageColorText()
        return _averageColorText
    }

    fun checkInternetConnection(context: Context): Boolean {
        val internetConnectionManager = InternetConnectionManager(context)
        return internetConnectionManager.isNetworkAvailable()
    }


    fun getMovieVideos(): LiveData<List<MovieVideoDto>> {
        _movieVideos = MoviesRepository.getMovieVideos()
        return _movieVideos
    }
}