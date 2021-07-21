package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDto
import com.johnsondev.doboshacademyapp.data.network.dto.GenreDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_IMG_PATH
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH

object DtoMapper {

    fun convertMovieFromDto(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            title = movieDto.title ?: "missing title",
            poster = "${POSTER_PATH}${movieDto.poster}",
            overview = movieDto.overview ?: "overview missing",
            backdrop = "${POSTER_PATH}${movieDto.backdropImg}",
            ratings = (movieDto.rating ?: 1f).div(2),
            numberOfRatings = movieDto.voteCount ?: 0,
            minimumAge = if (movieDto.adult == true) 16 else 13,
            runtime = movieDto.runtime,
            genres = movieDto.genres?.map { convertGenreFromDto(it) },
            actors = emptyList()
        )
    }

    private fun convertGenreFromDto(genreDto: GenreDto) = Genre(
        id = genreDto.id,
        name = genreDto.name
    )


    fun convertActorFromDto(actorDto: ActorDto): Actor {
        return Actor(
            id = actorDto.id,
            name = actorDto.name,
            picture = "${POSTER_PATH}${actorDto.profileImg}"
        )
    }

}