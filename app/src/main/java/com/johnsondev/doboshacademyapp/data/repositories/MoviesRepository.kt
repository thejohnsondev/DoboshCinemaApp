package com.johnsondev.doboshacademyapp.data.repositories

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
    private val moviesDatabase = App.getInstance().getMovieDatabase()

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()

    private var moviesList: MutableList<Movie> = mutableListOf()

    suspend fun loadMoviesFromNet() {
        moviesList.apply {
            val tempPopularMoviesList: List<Movie> = movieApi.getPopular().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }
            savePopularMoviesIdToDb(tempPopularMoviesList)
            addAll(tempPopularMoviesList)

            val tempTopRatedMoviesList: List<Movie> =
                movieApi.getTopRated().results.distinct().map {
                    DtoMapper.convertMovieFromDto(
                        movieApi.getMovieById(it.id)
                    )
                }
            saveTopRatedMoviesIdToDb(tempTopRatedMoviesList)
            addAll(tempTopRatedMoviesList)

            val tempUpcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    movieApi.getMovieById(it.id)
                )
            }
            saveUpcomingMoviesIdToDb(tempUpcomingMoviesList)
            addAll(tempUpcomingMoviesList)
        }
        saveAllMoviesToDb(moviesList)
        moviesList.forEach { saveGenresToDb(it.genres) }

    }

    private suspend fun saveAllMoviesToDb(moviesList: List<Movie>) {
        if (moviesList != null) {
            moviesDatabase.movieDao().deleteAllMovies()
            moviesDatabase.movieDao()
                .insertAllMovies(moviesList.map { Converter.convertMovieToMovieEntity(it) })
        }
    }

    private suspend fun savePopularMoviesIdToDb(moviesList: List<Movie>) {
        if (moviesList != null) {
            moviesDatabase.movieIdsDao().deleteAllPopularMoviesId()
            moviesDatabase.movieIdsDao()
                .insertPopularMoviesId(Converter.fromListOfStrings(moviesList.map { it.id.toString() }))
        }
    }

    private suspend fun saveTopRatedMoviesIdToDb(moviesList: List<Movie>) {
        if (moviesList != null) {
            moviesDatabase.movieIdsDao().deleteAllTopRatedMoviesId()
            moviesDatabase.movieIdsDao()
                .insertTopRatedMoviesId(Converter.fromListOfStrings(moviesList.map { it.id.toString() }))
        }
    }

    private suspend fun saveUpcomingMoviesIdToDb(moviesList: List<Movie>) {
        if (moviesList != null) {
            moviesDatabase.movieIdsDao().deleteAllUpcomingMoviesId()
            moviesDatabase.movieIdsDao()
                .insertUpcomingMoviesId(Converter.fromListOfStrings(moviesList.map { it.id.toString() }))
        }
        // TODO: 03.06.2021 use when() -> ...
    }

    suspend fun loadPopularMoviesFromDb() {
        if (allGenresList.isNullOrEmpty()) {
            loadGenresFromDb()
        }
        if (moviesList.isNullOrEmpty()) {
            moviesList.clear()
            moviesList.addAll(moviesDatabase.movieDao().getAllMovies().map {
                Converter.convertMovieEntityToMovie(it, allGenresList!!)
            })
        }
        val popularMoviesIdList =
            Converter.toListOfStrings(moviesDatabase.movieIdsDao().getPopularMoviesId())
        val tempResultList: MutableList<Movie> = mutableListOf()
        moviesList.forEach {
            if (popularMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        popularMoviesList = tempResultList
    }

    suspend fun loadTopRatedMoviesFromDb() {
        if (allGenresList.isNullOrEmpty()) {
            loadGenresFromDb()
        }
        if (moviesList.isNullOrEmpty()) {
            moviesList.clear()
            moviesList.addAll(moviesDatabase.movieDao().getAllMovies().map {
                Converter.convertMovieEntityToMovie(it, allGenresList!!)
            })
        }
        val topRatedMoviesIdList =
            Converter.toListOfStrings(moviesDatabase.movieIdsDao().getTopRatedMoviesId())
        val tempResultList: MutableList<Movie> = mutableListOf()
        moviesList.forEach {
            if (topRatedMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        topRatedMoviesList = tempResultList
    }

    suspend fun loadUpcomingMoviesFromDb() {
        if (allGenresList.isNullOrEmpty()) {
            loadGenresFromDb()
        }
        if (moviesList.isNullOrEmpty()) {
            moviesList.clear()
            moviesList.addAll(moviesDatabase.movieDao().getAllMovies().map {
                Converter.convertMovieEntityToMovie(it, allGenresList!!)
            })
        }
        val upcomingMoviesIdList =
            Converter.toListOfStrings(moviesDatabase.movieIdsDao().getUpcomingMoviesId())
        val tempResultList: MutableList<Movie> = mutableListOf()
        moviesList.forEach {
            if (upcomingMoviesIdList.contains(it.id.toString())) {
                tempResultList.add(it)
            }
        }
        upcomingMoviesList = tempResultList
    }


//    suspend fun loadPopularMoviesFromNet() {
//        popularMoviesList = movieApi.getPopular().results.distinct().map {
//            DtoMapper.convertMovieFromDto(
//                movieApi.getMovieById(it.id)
//            )
//        }
//        savePopularMoviesToDb(popularMoviesList)
//        popularMoviesList.forEach { saveGenresToDb(it.genres) }
//    }

//    suspend fun loadTopRatedMoviesFromNet() {
//        topRatedMoviesList = movieApi.getTopRated().results.distinct().map {
//            DtoMapper.convertMovieFromDto(
//                movieApi.getMovieById(it.id)
//            )
//        }
//        saveTopRatedMoviesToDb(topRatedMoviesList)
//        topRatedMoviesList.forEach { saveGenresToDb(it.genres) }
//    }

//    suspend fun loadUpcomingMoviesFromNet() {
//        upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
//            DtoMapper.convertMovieFromDto(
//                movieApi.getMovieById(it.id)
//            )
//        }
//        saveUpcomingMoviesToDb(upcomingMoviesList)
//        upcomingMoviesList.forEach { saveGenresToDb(it.genres) }
//    }

//    suspend fun loadPopularMoviesFromDb() {
//        loadGenresFromDb()
//        popularMoviesList = database.popularMoviesDao().getAllMovies().map {
//            Converter.convertPopularMovieEntityToMovie(it, allGenresList!!)
//        }
//    }


//
//    private suspend fun savePopularMoviesToDb(movies: List<Movie>?) {
//        if (movies != null) {
//            database.popularMoviesDao().deleteAllMovies()
//            database.popularMoviesDao()
//                .insertAll(movies.map { Converter.convertMovieToPopularMovieEntity(it) })
//        }
//    }
//
//    private suspend fun saveTopRatedMoviesToDb(movies: List<Movie>?) {
//        if (movies != null) {
//            database.topRatedMoviesDao().deleteAllMovies()
//            database.topRatedMoviesDao()
//                .insertAll(movies.map { Converter.convertMovieToTopRatedMovieEntity(it) })
//        }
//    }
//
//    private suspend fun saveUpcomingMoviesToDb(movies: List<Movie>?) {
//        if (movies != null) {
//            database.upcomingMoviesDao().deleteAllMovies()
//            database.upcomingMoviesDao()
//                .insertAll(movies.map { Converter.convertMovieToUpcomingMovieEntity(it) })
//        }
//    }

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

    fun getMovies(): List<Movie> {
        return moviesList
    }

    // TODO: 31.05.2021 change db schema, make 1 table with all movies, and 1 table with movies category and movie id
    // TODO: 31.05.2021 change toggle button group to viewpager 2


}