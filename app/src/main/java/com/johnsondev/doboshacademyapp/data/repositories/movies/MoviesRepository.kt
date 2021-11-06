package com.johnsondev.doboshacademyapp.data.repositories.movies

import androidx.lifecycle.MutableLiveData
import com.johnsondev.doboshacademyapp.data.models.SearchResultLists
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Genre
import com.johnsondev.doboshacademyapp.data.models.base.Movie
import com.johnsondev.doboshacademyapp.data.models.base.MovieDetails
import com.johnsondev.doboshacademyapp.data.network.dto.MovieImageDto
import com.johnsondev.doboshacademyapp.data.network.dto.MovieVideoDto

interface MoviesRepository {
    suspend fun getFavoriteMoviesIds(): MutableLiveData<List<Int>>
    suspend fun loadFavoritesMoviesFromDb()
    suspend fun insertMovieToFavorites(movieId: Int)
    suspend fun deleteMovieFromFavorites(movieId: Int)
    fun getFavoritesMovies(): MutableLiveData<List<Movie>>
    suspend fun search(query: String): SearchResultLists
    suspend fun loadSimilarMoviesById(movieId: Int)
    suspend fun loadRecommendationsByMovieId(id: Int)
    suspend fun loadMovieImages(id: Int)
    suspend fun loadMovieVideosById(id: Int)
    suspend fun loadMovieById(id: Int)
    suspend fun loadGenresList()
    suspend fun loadMoviesByGenreId(id: Int)
    suspend fun loadPopularActorsList()
    fun getPopularActors(): MutableLiveData<List<Actor>>
    fun getMoviesByGenre(): MutableLiveData<List<Movie>>
    fun getGenresList(): MutableLiveData<List<Genre>>
    fun getMovieVideos(): MutableLiveData<List<MovieVideoDto>>
    fun getCurrentMovie(): MutableLiveData<MovieDetails>
    fun getMovieImages(): MutableLiveData<Map<String, List<MovieImageDto>>>
    fun getRecommendations(): MutableLiveData<List<Movie>>
    fun getSimilarMovies(): MutableLiveData<List<Movie>>
    suspend fun loadPopularMoviesFromNet()
    suspend fun loadTopRatedMoviesFromNet()
    suspend fun loadUpcomingMoviesFromNet()
    fun getTopRatedMovies(): List<Movie>
    fun getPopularMovies(): List<Movie>
    fun getUpcomingMovies(): List<Movie>
}