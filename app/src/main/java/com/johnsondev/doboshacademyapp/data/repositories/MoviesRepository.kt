package com.johnsondev.doboshacademyapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.utilities.Constants.LANG_RU
import com.johnsondev.doboshacademyapp.utilities.Constants.POPULAR_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.TOP_RATED_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.UPCOMING_MOVIES_TYPE
import com.johnsondev.doboshacademyapp.utilities.Converter
import com.johnsondev.doboshacademyapp.utilities.DtoMapper

object MoviesRepository {

    private var allGenresList: List<Genre>? = null

    private val movieApi = NetworkService.MOVIE_API
    private val moviesDatabase = App.getInstance().getMovieDatabase()

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()

    private var allMoviesList: MutableList<Movie> = mutableListOf()

    private var actorImgAverageColorBody = MutableLiveData<Int>()
    private var actorImgAverageColorText = MutableLiveData<Int>()

    private var movieVideos = MutableLiveData<List<MovieVideoDto>>()

    private var currentMovie = MutableLiveData<Movie>()

    suspend fun loadMovieVideosById(id: Int){
        movieVideos.postValue(movieApi.getMovieVideos(id, LANG_RU).results)
    }

    fun getMovieVideos(): MutableLiveData<List<MovieVideoDto>> = movieVideos

    suspend fun loadMovieById(id: Int) {
        currentMovie.value = DtoMapper.convertMovieFromDto(movieApi.getMovieById(id))
    }

    fun getCurrentMovie(): MutableLiveData<Movie> = currentMovie

    fun setAverageColor(body: Int, text: Int) {
        actorImgAverageColorBody.postValue(body)
        actorImgAverageColorText.postValue(text)
    }

    fun getAverageColorBody(): MutableLiveData<Int> = actorImgAverageColorBody
    fun getAverageColorText(): MutableLiveData<Int> = actorImgAverageColorText


    suspend fun loadMoviesFromNet() {
        allMoviesList.apply {
            popularMoviesList = movieApi.getPopular().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }
            savePopularMoviesIdToDb(popularMoviesList)
            addAll(popularMoviesList)

            topRatedMoviesList =
                movieApi.getTopRated().results.distinct().map {
                    DtoMapper.convertMovieFromDto(
                        movieApi.getMovieById(it.id)
                    )
                }
            saveTopRatedMoviesIdToDb(topRatedMoviesList)
            addAll(topRatedMoviesList)

            upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }
            saveUpcomingMoviesIdToDb(upcomingMoviesList)
            addAll(upcomingMoviesList)
        }
        saveAllMoviesToDb(allMoviesList)
        allMoviesList.forEach { saveGenresToDb(it.genres) }


    }

    fun getAllMoviesFromNet(): List<Movie> = allMoviesList.distinctBy { it.id }

    private suspend fun saveAllMoviesToDb(moviesList: List<Movie>) {
        moviesDatabase.movieDao().deleteAllMovies()
        moviesDatabase.movieDao()
            .insertAllMovies(moviesList.map { Converter.convertMovieToMovieEntity(it) })
    }

    private suspend fun savePopularMoviesIdToDb(moviesList: List<Movie>) {
        moviesDatabase.movieIdsDao().deleteMovieIdsList(POPULAR_MOVIES_TYPE)
        moviesDatabase.movieIdsDao()
            .insertMovieIdsList(
                POPULAR_MOVIES_TYPE,
                Converter.fromListOfStrings(moviesList.map { it.id.toString() })
            )
    }

    private suspend fun saveTopRatedMoviesIdToDb(moviesList: List<Movie>) {
        moviesDatabase.movieIdsDao().deleteMovieIdsList(TOP_RATED_MOVIES_TYPE)
        moviesDatabase.movieIdsDao()
            .insertMovieIdsList(
                TOP_RATED_MOVIES_TYPE,
                Converter.fromListOfStrings(moviesList.map { it.id.toString() })
            )
    }

    private suspend fun saveUpcomingMoviesIdToDb(moviesList: List<Movie>) {
        moviesDatabase.movieIdsDao().deleteMovieIdsList(UPCOMING_MOVIES_TYPE)
        moviesDatabase.movieIdsDao()
            .insertMovieIdsList(
                UPCOMING_MOVIES_TYPE,
                Converter.fromListOfStrings(moviesList.map { it.id.toString() })
            )
    }

    suspend fun loadPopularMoviesFromDb() {
        loadGenresAndMoviesFromDb()
        val popularMoviesIdList =
            Converter.toListOfStrings(
                moviesDatabase.movieIdsDao().getMovieIdsList(POPULAR_MOVIES_TYPE)
            )
        val tempResultList: MutableList<Movie> = mutableListOf()
        allMoviesList.forEach {
            if (popularMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        popularMoviesList = tempResultList
    }

    suspend fun loadTopRatedMoviesFromDb() {
        loadGenresAndMoviesFromDb()
        val topRatedMoviesIdList =
            Converter.toListOfStrings(
                moviesDatabase.movieIdsDao().getMovieIdsList(TOP_RATED_MOVIES_TYPE)
            )
        val tempResultList: MutableList<Movie> = mutableListOf()
        allMoviesList.forEach {
            if (topRatedMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        topRatedMoviesList = tempResultList
    }

    suspend fun loadUpcomingMoviesFromDb() {
        loadGenresAndMoviesFromDb()
        val upcomingMoviesIdList =
            Converter.toListOfStrings(
                moviesDatabase.movieIdsDao().getMovieIdsList(UPCOMING_MOVIES_TYPE)
            )
        val tempResultList: MutableList<Movie> = mutableListOf()
        allMoviesList.forEach {
            if (upcomingMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        upcomingMoviesList = tempResultList
    }

    fun getMovieByIdFromDb(movieId: Int): Movie {
        val movieById = allMoviesList.find { it.id == movieId } ?: Movie()

        return if (allMoviesList.any { it.id == movieId }) {
            movieById
        } else {
            Movie(title = "Unknown movie")
        }
    }

    private suspend fun loadGenresAndMoviesFromDb() {
        if (allGenresList.isNullOrEmpty()) {
            loadGenresFromDb()
        }
        if (allMoviesList.isNullOrEmpty()) {
            allMoviesList.clear()
            allMoviesList.addAll(moviesDatabase.movieDao().getAllMovies().map {
                Converter.convertMovieEntityToMovie(it, allGenresList!!)
            })
        }
    }

    suspend fun loadAllMovieFromDb(): List<Movie> {
        if (allGenresList.isNullOrEmpty()) {
            loadGenresFromDb()
        }
        return moviesDatabase.movieDao().getAllMovies().map {
            Converter.convertMovieEntityToMovie(it, allGenresList!!)
        }
    }

    private suspend fun saveGenresToDb(genres: List<Genre>?) {
        if (genres != null) {
            moviesDatabase.genreDao().insertAll(genres.map { Converter.convertToGenreEntity(it) })
        }
    }

    private suspend fun loadGenresFromDb() {
        allGenresList = moviesDatabase.genreDao().selectAllGenres().map {
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