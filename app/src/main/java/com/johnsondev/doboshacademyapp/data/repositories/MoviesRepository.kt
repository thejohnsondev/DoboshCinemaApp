package com.johnsondev.doboshacademyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.utilities.Converter
import com.johnsondev.doboshacademyapp.utilities.DtoMapper

object MoviesRepository {

    private var allGenresList: List<Genre>? = null

    private val movieApi = NetworkService.MOVIE_API
    private val database = App.getInstance().getDatabase()

    private var topRatedMovies = MutableLiveData<List<Movie>>()
    private var topRatedMoviesList: List<MovieDto> = listOf()

    private lateinit var popularMovies: List<Movie>
    private var popularMoviesList: List<MovieDto> = listOf()

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    private var upcomingMoviesList: List<MovieDto> = listOf()

    private var movieById = MutableLiveData<Movie>()


    suspend fun loadPopularMoviesFromNet() {
        popularMovies = movieApi.getPopular().results.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        }
        saveMoviesToDb(popularMovies)
        popularMovies.forEach { saveGenresToDb(it.genres) }
        Log.d("TAG", "loadFromNet")
    }

    suspend fun loadPopularMoviesFromDb() {
        loadGenresFromDb()
        popularMovies = database.movieDao().getAllMovies().map {
            Converter.convertMovieEntityToMovie(it, allGenresList!!)
        }
        Log.d("TAG", "loadFromDb ")
    }

    suspend fun loadTopRatedMovies() {
        topRatedMoviesList = movieApi.getTopRated().results
        topRatedMovies.postValue(topRatedMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadUpcomingMovies() {
        upcomingMoviesList = movieApi.getUpcoming().results
        upcomingMovies.postValue(upcomingMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadMovieById(id: Int): LiveData<Movie> {
        movieById.postValue(
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(id)
            )
        )
        return movieById
    }

    private suspend fun saveMoviesToDb(movies: List<Movie>?) {
        if (movies != null) {
            database.movieDao().insertAll(movies.map { Converter.convertMovieToMovieEntity(it) })
        }
    }

    private suspend fun saveGenresToDb(genres: List<Genre>?) {
        if (genres != null) {
            database.genreDao().insertAll(genres.map { Converter.convertToGenreEntity(it) })
        }
    }

    private suspend fun loadGenresFromDb() {
        allGenresList = database.genreDao().selectAllGenres().map {
            Converter.convertGenreEntityToGenre(it)
        }
    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        return topRatedMovies
    }

    fun getPopularMovies(): List<Movie> {
        return popularMovies
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        return upcomingMovies
    }


}