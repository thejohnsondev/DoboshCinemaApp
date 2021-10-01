package com.johnsondev.doboshacademyapp.utilities

import com.johnsondev.doboshacademyapp.data.models.base.*
import com.johnsondev.doboshacademyapp.data.network.dto.*
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH

object DtoMapper {

    fun convertCountryFromDto(productionCountryDto: ProductionCountryDto) = ProductionCountry(
        countryCode = productionCountryDto.countryCode,
        countryName = checkCountryName(productionCountryDto.countryName)
    )

    fun convertMovieDetailsFromDto(movieDetailsDto: MovieDetailsDto) = MovieDetails(
        id = movieDetailsDto.id,
        title = movieDetailsDto.title,
        poster = "${POSTER_PATH}${movieDetailsDto.poster}",
        overview = movieDetailsDto.overview,
        backdrop = "${POSTER_PATH}${movieDetailsDto.backdropImg}",
        ratings = (movieDetailsDto.rating).div(2),
        numberOfRatings = movieDetailsDto.voteCount,
        minimumAge = if (movieDetailsDto.adult) 16 else 13,
        runtime = movieDetailsDto.runtime,
        genres = movieDetailsDto.genres.map { convertGenreFromDto(it) },
        actors = emptyList(),
        budget = movieDetailsDto.budget,
        revenue = movieDetailsDto.revenue,
        origLanguage = movieDetailsDto.origLanguage,
        origTitle = movieDetailsDto.origTitle,
        productionCompanies = movieDetailsDto.productionCompanies,
        productionCountries = movieDetailsDto.productionCountries,
        releaseDate = revertDate(movieDetailsDto.releaseDate),
        status = movieDetailsDto.status,
        tagLine = movieDetailsDto.tagLine
    )

    fun convertMovieFromDto(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            title = movieDto.title,
            poster = "${POSTER_PATH}${movieDto.poster}",
            overview = movieDto.overview,
            backdrop = "${POSTER_PATH}${movieDto.backdropImg}",
            ratings = (movieDto.rating).div(2),
            numberOfRatings = movieDto.voteCount,
            minimumAge = if (movieDto.adult) 16 else 13,
            runtime = movieDto.runtime,
            genres = movieDto.genres.map { convertGenreFromDto(it) },
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


    fun convertCrewMemberFromDto(crewMemberDto: CrewMemberDto) = CrewMember(
        id = crewMemberDto.id,
        name = crewMemberDto.name,
        picture = "${POSTER_PATH}${crewMemberDto.profileImg}",
        job = crewMemberDto.job
    )

}