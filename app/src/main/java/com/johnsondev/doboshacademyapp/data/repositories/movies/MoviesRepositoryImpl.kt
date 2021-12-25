package com.johnsondev.doboshacademyapp.data.repositories.movies

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.db.FavoritesDb
import com.johnsondev.doboshacademyapp.data.db.entities.FavoriteEntity
import com.johnsondev.doboshacademyapp.data.models.SearchResultLists
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.models.base.MovieDetails
import com.johnsondev.doboshacademyapp.data.network.api.MovieApi
import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto
import com.johnsondev.doboshacademyapp.data.network.exception.ConnectionErrorException
import com.johnsondev.doboshacademyapp.data.network.exception.UnexpectedErrorException
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.FAVORITE_MOVIE_ENTITY_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.LANG_RU
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.DtoMapper
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class MoviesRepositoryImpl(
    private val movieApi: MovieApi,
    private val favoritesDatabase: FavoritesDb
) : MoviesRepository {

    private var popularMoviesList: List<Movie> = listOf()
    private var topRatedMoviesList: List<Movie> = listOf()
    private var upcomingMoviesList: List<Movie> = listOf()
    private var genresList = MutableLiveData<List<Genre>>()
    private var moviesByGenre = MutableLiveData<List<Movie>>()
    private var popularActorsList = MutableLiveData<List<Actor>>()

    private var favoriteMovies = MutableLiveData<List<Movie>>()
    private var favoriteMoviesIds = MutableLiveData<List<Int>>()

    private var currentMovieDetails = MutableLiveData<MovieDetails>()
    private var movieRecommendations = MutableLiveData<List<Movie>>()
    private var similarMovies = MutableLiveData<List<Movie>>()
    private var movieVideos = MutableLiveData<List<MovieVideoDto>>()
    private var movieImages = MutableLiveData<Map<String, List<MovieImageDto>>>()

    override suspend fun getFavoriteMoviesIds(): MutableLiveData<List<Int>> {
        try {
            favoriteMoviesIds.value = favoritesDatabase.favoritesDao()
                .getFavoritesEntityIdByType(FAVORITE_MOVIE_ENTITY_TYPE)
        } catch (e: Exception) {
            handleExceptions(e)
        }
        return favoriteMoviesIds
    }


    override suspend fun loadFavoritesMoviesFromDb() {
        try {
            favoriteMovies.value = favoritesDatabase.favoritesDao()
                .getFavoritesEntityIdByType(FAVORITE_MOVIE_ENTITY_TYPE).map { id ->
                    DtoMapper.convertMovieFromDto(movieApi.getMovieById(id))
                }

        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun insertMovieToFavorites(movieId: Int) {

        try {
            favoritesDatabase.favoritesDao().insertFavoriteEntity(
                FavoriteEntity(
                    entityType = FAVORITE_MOVIE_ENTITY_TYPE,
                    entityId = movieId
                )
            )
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun deleteMovieFromFavorites(movieId: Int) {
        try {
            favoritesDatabase.favoritesDao()
                .deleteFavoriteEntity(FAVORITE_MOVIE_ENTITY_TYPE, movieId)
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    override fun getFavoritesMovies(): MutableLiveData<List<Movie>> = favoriteMovies

    override suspend fun search(query: String): SearchResultLists {
        var moviesSearchResult: List<Movie> = listOf()
        var actorsSearchResult: List<Actor> = listOf()

        try {
            moviesSearchResult =
                movieApi.multiSearch(query).results.filter { it.mediaType == "movie" }
                    .distinct().map {
                        DtoMapper.convertMovieFromDto(movieApi.getMovieById(it.id))
                    }
            actorsSearchResult =
                movieApi.multiSearch(query).results.filter { it.mediaType == "person" }
                    .distinct().map {
                        DtoMapper.convertActorFromDto(movieApi.getActor(it.id))
                    }
        } catch (e: Exception) {
            handleExceptions(e)
        }

        return SearchResultLists(moviesSearchResult, actorsSearchResult)
    }

    override suspend fun loadSimilarMoviesById(movieId: Int) {
        try {
            similarMovies.postValue(
                movieApi.getSimilarMoviesByMovieId(movieId).results.distinct().map {
                    DtoMapper.convertMovieFromDto(it)
                })
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadRecommendationsByMovieId(id: Int) {
        try {
            movieRecommendations.postValue(
                movieApi.getRecommendationsByMovieId(id).results.distinct().map {
                    DtoMapper.convertMovieFromDto(it)
                })
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadMovieImages(id: Int) {
        try {
            val movieImagesFromApi = movieApi.getMovieImages(id)
            movieImages.postValue(
                mapOf(
                    POSTER_KEY to movieImagesFromApi.posters,
                    BACKDROP_KEY to movieImagesFromApi.backdrops
                )
            )
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadMovieVideosById(id: Int) {
        try {
            movieVideos.postValue(movieApi.getMovieVideos(id, LANG_RU).results)
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    override suspend fun loadMovieById(id: Int) {
        try {
            currentMovieDetails.value =
                DtoMapper.convertMovieDetailsFromDto(movieApi.getMovieDetailsById(id))
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadGenresList() {
        try {
            genresList.value = movieApi.getGenresList().genres.distinct().map {
                DtoMapper.convertGenreFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadMoviesByGenreId(id: Int) {
        try {
            moviesByGenre.value = movieApi.getMoviesListByGenreId(id).results.distinct().map {
                DtoMapper.convertMovieFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override suspend fun loadPopularActorsList() {
        try {
            popularActorsList.value = movieApi.getPopularActors().results.distinct().map {
                DtoMapper.convertActorFromDto(it)
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }
    }

    override fun getPopularActors(): MutableLiveData<List<Actor>> = popularActorsList

    override fun getMoviesByGenre(): MutableLiveData<List<Movie>> = moviesByGenre

    override fun getGenresList(): MutableLiveData<List<Genre>> = genresList

    override fun getMovieVideos(): MutableLiveData<List<MovieVideoDto>> = movieVideos

    override fun getCurrentMovie(): MutableLiveData<MovieDetails> = currentMovieDetails

    override fun getMovieImages(): MutableLiveData<Map<String, List<MovieImageDto>>> = movieImages

    override fun getRecommendations(): MutableLiveData<List<Movie>> = movieRecommendations

    override fun getSimilarMovies(): MutableLiveData<List<Movie>> = similarMovies


    override suspend fun loadPopularMoviesFromNet() {
        try {
            popularMoviesList = movieApi.getPopular().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    override suspend fun loadTopRatedMoviesFromNet() {
        try {
            topRatedMoviesList = movieApi.getTopRated().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }

    override suspend fun loadUpcomingMoviesFromNet() {
        try {
            upcomingMoviesList = movieApi.getUpcoming().results.distinct().map {
                DtoMapper.convertMovieFromDto(
                    it
                )
            }
        } catch (e: Exception) {
            handleExceptions(e)
        }

    }


    override fun getTopRatedMovies(): List<Movie> {
        return topRatedMoviesList
    }

    override fun getPopularMovies(): List<Movie> {
        return popularMoviesList
    }

    override fun getUpcomingMovies(): List<Movie> {
        return upcomingMoviesList
    }

    private fun handleExceptions(e: Exception) {
        throw when (e) {
            is IOException, is HttpException, is TimeoutException -> ConnectionErrorException()
            else -> UnexpectedErrorException()
        }
    }


}