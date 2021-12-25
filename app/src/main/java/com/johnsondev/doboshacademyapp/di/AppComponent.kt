package com.johnsondev.doboshacademyapp.di

import com.johnsondev.doboshacademyapp.data.services.MovieDbUpdateWorker
import com.johnsondev.doboshacademyapp.ui.actordetails.ActorDetailsFragment
import com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments.ActorDetailsAboutFragment
import com.johnsondev.doboshacademyapp.ui.actordetails.pagerfragments.ActorDetailsMoviesFragment
import com.johnsondev.doboshacademyapp.ui.favorite.FavoriteFragment
import com.johnsondev.doboshacademyapp.ui.favorite.pagerfragments.FavoriteActorsFragment
import com.johnsondev.doboshacademyapp.ui.favorite.pagerfragments.FavoriteMoviesFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.MoviesDetailsFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.SpecificImagesFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments.MovieDetailsActorsFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments.MovieDetailsInfoFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments.MovieDetailsRecommendFragment
import com.johnsondev.doboshacademyapp.ui.moviedetails.pagerfragments.MovieDetailsTrailersFragment
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListFragment
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListViewModel
import com.johnsondev.doboshacademyapp.ui.movielist.specificlists.SpecificActorsListFragment
import com.johnsondev.doboshacademyapp.ui.movielist.specificlists.SpecificMoviesListFragment
import com.johnsondev.doboshacademyapp.ui.search.SearchFragment
import com.johnsondev.doboshacademyapp.ui.settings.SettingsFragment
import com.johnsondev.doboshacademyapp.ui.settings.sectionfragments.AboutFragment
import com.johnsondev.doboshacademyapp.ui.settings.sectionfragments.SettingsContentFragment
import com.johnsondev.doboshacademyapp.ui.settings.sectionfragments.SettingsGeneralFragment
import com.johnsondev.doboshacademyapp.ui.settings.sectionfragments.SettingsInterfaceFragment
import com.johnsondev.doboshacademyapp.ui.splash.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(moviesListFragment: MoviesListFragment)
    fun inject(moviesListViewModel: MoviesListViewModel)
    fun inject(splashScreenActivity: SplashScreenActivity)
    fun inject(specificMoviesListFragment: SpecificMoviesListFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(specificActorsListFragment: SpecificActorsListFragment)
    fun inject(settingsInterfaceFragment: SettingsInterfaceFragment)
    fun inject(settingsGeneralFragment: SettingsGeneralFragment)
    fun inject(settingsContentFragment: SettingsContentFragment)
    fun inject(aboutFragment: AboutFragment)
    fun inject(moviesDetailsFragment: MoviesDetailsFragment)
    fun inject(movieDetailsTrailersFragment: MovieDetailsTrailersFragment)
    fun inject(movieDetailsRecommendFragment: MovieDetailsRecommendFragment)
    fun inject(movieDetailsInfoFragment: MovieDetailsInfoFragment)
    fun inject(movieDetailsActorsFragment: MovieDetailsActorsFragment)
    fun inject(specificImagesFragment: SpecificImagesFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)
    fun inject(favoriteActorsFragment: FavoriteActorsFragment)
    fun inject(actorDetailsFragment: ActorDetailsFragment)
    fun inject(actorDetailsMoviesFragment: ActorDetailsMoviesFragment)
    fun inject(actorDetailsAboutFragment: ActorDetailsAboutFragment)
    fun inject(movieDbUpdateWorker: MovieDbUpdateWorker)
}