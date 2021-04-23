package com.johnsondev.doboshacademyapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.App
import com.johnsondev.doboshacademyapp.data.db.MoviesDb
import com.johnsondev.doboshacademyapp.data.db.entities.GenreEntity
import com.johnsondev.doboshacademyapp.data.db.entities.MovieEntity
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.data.network.NetworkService
import com.johnsondev.doboshacademyapp.utilities.DtoMapper

object MoviesRepository {

    private val movieApi = NetworkService.MOVIE_API

    //    private val database = MoviesDb.getDatabase()
    private val database = App.getInstance().getDatabase()


    private var topRatedMovies = MutableLiveData<List<Movie>>()
    private var topRatedMoviesList: List<MovieDto> = listOf()

    private var popularMovies = MutableLiveData<List<Movie>>()
    private var popularMoviesList: List<MovieDto> = listOf()

    private var upcomingMovies = MutableLiveData<List<Movie>>()
    private var upcomingMoviesList: List<MovieDto> = listOf()

    private var movieById = MutableLiveData<Movie>()

    suspend fun loadTopRatedMovies() {
        topRatedMoviesList = movieApi.getTopRated().results
        topRatedMovies.postValue(topRatedMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
    }

    suspend fun loadPopularMovies() {
        popularMoviesList = movieApi.getPopular().results
        popularMovies.postValue(popularMoviesList.distinct().map {
            DtoMapper.convertMovieFromDto(
                movieApi.getMovieById(it.id)
            )
        })
        saveMovies(popularMovies.value)
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

    private suspend fun saveMovies(movies: List<Movie>?) {
        if (movies != null) {
            database.movieDao().insertAll(movies.map { convertMovieToMovieEntity(it) })

//            movies.forEach { movie ->
//                movie.genres?.let { it ->
//                    database.genreDao().insertAll(it.map {
//                        convertToGenreEntity(it)
//                    })
//                }
//                if (!movie.genres.isNullOrEmpty()) {
//                    for (genre in movie.genres) {
//                        database.movieWithGenre()
//                            .insert(MovieGenreJoin(movie.id, genre.id))
//                    }
//                }
//            }
        }
    }

    private fun convertMovieToMovieEntity(movie: Movie) = MovieEntity(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        poster = movie.poster,
        backdrop = movie.backdrop,
        ratings = movie.ratings,
        numberOfRatings = movie.numberOfRatings,
        minimumAge = movie.minimumAge,
        runtime = movie.runtime,
        genresId = fromListOfStrings((movie.genres!!.map { it.id.toString() })),
//        genresId = ",",
        actorsId = ","
    )

    private fun convertMovieEntityToMovie(movieEntity: MovieEntity) = Movie(
        id = movieEntity.id!!,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.poster,
        backdrop = movieEntity.backdrop,
        ratings = movieEntity.ratings,
        numberOfRatings = movieEntity.numberOfRatings,
        minimumAge = movieEntity.minimumAge,
        runtime = movieEntity.runtime,
//        genres = movieEntity.genresId.map { convertGenreEntityToGenre(it) },
        genres = emptyList(),
        actors = emptyList()
    )

    private fun convertToGenreEntity(genre: Genre) = GenreEntity(
        id = genre.id,
        name = genre.name
    )

    private fun convertGenreEntityToGenre(genreEntity: GenreEntity) = Genre(
        id = genreEntity.id,
        name = genreEntity.name
    )

    private fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }

    private fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }


    fun getTopRatedMovies(): LiveData<List<Movie>> {
        return topRatedMovies
    }

    fun getPopularMovies(): LiveData<List<Movie>> {
        return popularMovies
    }

    fun getUpcomingMovies(): LiveData<List<Movie>> {
        return upcomingMovies
    }


}