package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.data.db.entities.GenreEntity
import com.johnsondev.doboshacademyapp.data.db.entities.PopularMoviesEntity
import com.johnsondev.doboshacademyapp.data.db.entities.TopRatedMoviesEntity
import com.johnsondev.doboshacademyapp.data.db.entities.UpcomingMoviesEntity
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie

object Converter {

    fun convertToGenreEntity(genre: Genre) = GenreEntity(
        id = genre.id,
        name = genre.name
    )

    fun convertGenreEntityToGenre(genreEntity: GenreEntity) = Genre(
        id = genreEntity.id,
        name = genreEntity.name
    )

    fun convertMovieToPopularMovieEntity(movie: Movie) = PopularMoviesEntity(
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
        actorsId = ","
    )

    fun convertMovieToTopRatedMovieEntity(movie: Movie) = TopRatedMoviesEntity(
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
        actorsId = ","
    )

    fun convertMovieToUpcomingMovieEntity(movie: Movie) = UpcomingMoviesEntity(
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
        actorsId = ","
    )

    fun convertPopularMovieEntityToMovie(movieEntity: PopularMoviesEntity, allGenreList: List<Genre>) =
        Movie(
            id = movieEntity.id!!,
            title = movieEntity.title,
            overview = movieEntity.overview,
            poster = movieEntity.poster,
            backdrop = movieEntity.backdrop,
            ratings = movieEntity.ratings,
            numberOfRatings = movieEntity.numberOfRatings,
            minimumAge = movieEntity.minimumAge,
            runtime = movieEntity.runtime,
            genres = filterGenre(allGenreList, movieEntity.genresId),
            actors = emptyList()
        )

    fun convertTopRatedMovieEntityToMovie(movieEntity: TopRatedMoviesEntity, allGenreList: List<Genre>) =
        Movie(
            id = movieEntity.id!!,
            title = movieEntity.title,
            overview = movieEntity.overview,
            poster = movieEntity.poster,
            backdrop = movieEntity.backdrop,
            ratings = movieEntity.ratings,
            numberOfRatings = movieEntity.numberOfRatings,
            minimumAge = movieEntity.minimumAge,
            runtime = movieEntity.runtime,
            genres = filterGenre(allGenreList, movieEntity.genresId),
            actors = emptyList()
        )

    fun convertUpcomingMovieEntityToMovie(movieEntity: UpcomingMoviesEntity, allGenreList: List<Genre>) =
        Movie(
            id = movieEntity.id!!,
            title = movieEntity.title,
            overview = movieEntity.overview,
            poster = movieEntity.poster,
            backdrop = movieEntity.backdrop,
            ratings = movieEntity.ratings,
            numberOfRatings = movieEntity.numberOfRatings,
            minimumAge = movieEntity.minimumAge,
            runtime = movieEntity.runtime,
            genres = filterGenre(allGenreList, movieEntity.genresId),
            actors = emptyList()
        )

    private fun filterGenre(allGenreList: List<Genre>, movieEntityGenreId: String): List<Genre> {
        val genreList: MutableList<Genre> = mutableListOf()
        val genresIdString: List<String> = toListOfStrings(movieEntityGenreId)
        val genresId: MutableList<Int> = mutableListOf()

        genresIdString.forEach {
            genresId.add(it.toInt())
        }

        allGenreList.forEach { genre ->
            genresId.forEach { id ->
                if (genre.id == id) {
                    genreList.add(genre)
                }
            }
        }
        return genreList
    }

    private fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }

    private fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }

}