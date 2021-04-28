package com.johnsondev.doboshacademyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.utilities.Converter
import com.johnsondev.doboshacademyapp.utilities.DtoMapper

object MoviesRepository {

    private var allGenresList: List<Genre>? = null

    private val movieApi = NetworkService.MOVIE_API
    private val database = App.getInstance().getDatabase()

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()


    suspend fun loadPopularMoviesFromNet() {
        popularMoviesList = movieApi.getPopular().results.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        }
        savePopularMoviesToDb(popularMoviesList)
        popularMoviesList.forEach { saveGenresToDb(it.genres) }
    }

    suspend fun loadTopRatedMoviesFromNet() {
        topRatedMoviesList = movieApi.getTopRated().results.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        }
        saveTopRatedMoviesToDb(topRatedMoviesList)
        topRatedMoviesList.forEach { saveGenresToDb(it.genres) }
    }

    suspend fun loadUpcomingMoviesFromNet() {
        upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        }
        saveUpcomingMoviesToDb(upcomingMoviesList)
        upcomingMoviesList.forEach { saveGenresToDb(it.genres) }
    }

    suspend fun loadPopularMoviesFromDb() {
        loadGenresFromDb()
        popularMoviesList = database.popularMoviesDao().getAllMovies().map {
            Converter.convertPopularMovieEntityToMovie(it, allGenresList!!)
        }
    }

    suspend fun loadTopRatedMoviesFromDb() {
        loadGenresFromDb()
        topRatedMoviesList = database.topRatedMoviesDao().getAllMovies().map {
            Converter.convertTopRatedMovieEntityToMovie(it, allGenresList!!)
        }
    }

    suspend fun loadUpcomingMoviesFromDb() {
        loadGenresFromDb()
        upcomingMoviesList = database.upcomingMoviesDao().getAllMovies().map {
            Converter.convertUpcomingMovieEntityToMovie(it, allGenresList!!)
        }
    }


    private suspend fun savePopularMoviesToDb(movies: List<Movie>?) {
        if (movies != null) {
            database.popularMoviesDao()
                .insertAll(movies.map { Converter.convertMovieToPopularMovieEntity(it) })
        }
    }

    private suspend fun saveTopRatedMoviesToDb(movies: List<Movie>?) {
        if (movies != null) {
            database.topRatedMoviesDao()
                .insertAll(movies.map { Converter.convertMovieToTopRatedMovieEntity(it) })
        }
    }

    private suspend fun saveUpcomingMoviesToDb(movies: List<Movie>?) {
        if (movies != null) {
            database.upcomingMoviesDao()
                .insertAll(movies.map { Converter.convertMovieToUpcomingMovieEntity(it) })
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

    fun getTopRatedMovies(): List<Movie> {
        return topRatedMoviesList
    }

    fun getPopularMovies(): List<Movie> {
        return popularMoviesList
    }

    fun getUpcomingMovies(): List<Movie> {
        return upcomingMoviesList
    }


}