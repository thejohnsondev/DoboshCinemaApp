package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Genre
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.models.MovieDetails
import com.johnsondev.doboshacademyapp.data.network.dto.ActorDto
import com.johnsondev.doboshacademyapp.data.network.dto.GenreDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDetailsDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieDto
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH

object DtoMapper {

    fun convertMovieDetailsFromDto(movieDetailsDto: MovieDetailsDto) = MovieDetails(
        id = movieDetailsDto.id,
        title = movieDetailsDto.title ?: "missing title",
        poster = "${POSTER_PATH}${movieDetailsDto.poster}",
        overview = movieDetailsDto.overview ?: "overview missing",
        backdrop = "${POSTER_PATH}${movieDetailsDto.backdropImg}",
        ratings = (movieDetailsDto.rating ?: 1f).div(2),
        numberOfRatings = movieDetailsDto.voteCount ?: 0,
        minimumAge = if (movieDetailsDto.adult == true) 16 else 13,
        runtime = movieDetailsDto.runtime,
        genres = movieDetailsDto.genres?.map { convertGenreFromDto(it) },
        actors = emptyList(),
        budget = movieDetailsDto.budget ?: 0,
        revenue = movieDetailsDto.revenue ?: 0,
        origLanguage = movieDetailsDto.origLanguage ?: "missing info",
        origTitle = movieDetailsDto.origTitle ?: "missing info",
        productionCompanies = movieDetailsDto.productionCompanies,
        productionCountries = movieDetailsDto.productionCountries,
        releaseDate = movieDetailsDto.releaseDate ?: "missing info",
        status = movieDetailsDto.status ?: "missing info",
        tagLine = movieDetailsDto.tagLine ?: "missing info"
    )

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

    fun convertGenreFromDto(genreDto: GenreDto) = Genre(
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