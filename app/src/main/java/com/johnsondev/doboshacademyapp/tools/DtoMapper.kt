package com.johnsondev.doboshacademyapp.tools

import com.johnsondev.doboshacademyapp.model.data.Actor
import com.johnsondev.doboshacademyapp.model.data.Genre
import com.johnsondev.doboshacademyapp.model.data.Movie
import com.johnsondev.doboshacademyapp.model.dto.ActorDto
import com.johnsondev.doboshacademyapp.model.dto.GenreDto
import com.johnsondev.doboshacademyapp.model.dto.MovieDto
import com.johnsondev.doboshacademyapp.tools.Constants.ACTOR_IMG_PATH
import com.johnsondev.doboshacademyapp.tools.Constants.POSTER_PATH

object DtoMapper {

    fun convertMovieFromDto(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            title = movieDto.title,
            poster = "${POSTER_PATH}${movieDto.poster}",
            overview = movieDto.overview,
            backdrop = "${POSTER_PATH}${movieDto.backdropImg}",
            ratings = movieDto.rating / 2,
            numberOfRatings = movieDto.voteCount,
            minimumAge = if (movieDto.adult) 16 else 13,
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
            picture = "${ACTOR_IMG_PATH}${actorDto.profileImg}"
        )
    }

}