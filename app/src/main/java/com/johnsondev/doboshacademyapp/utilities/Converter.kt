package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.data.db.entities.*
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

    fun convertMovieToMovieEntity(movie: Movie) = MovieEntity(
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

    fun convertMovieEntityToMovie(movieEntity: MovieEntity, allGenreList: List<Genre>) =
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

    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }

    fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }

}