package com.johnsondev.doboshacademyapp.views.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ID
import com.johnsondev.doboshacademyapp.utilities.addFragment
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragment
import com.johnsondev.doboshacademyapp.views.movielist.MoviesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
        loadData(savedInstanceState)

    }

    private fun initViews(){
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController)
    }

    private fun loadData(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            val bundle = Bundle()

            bundle.putBoolean(
                Constants.CONNECTION_ERROR_ARG,
                intent.getBooleanExtra(Constants.CONNECTION_ERROR_EXTRA, false)
            )
            val movieId = intent.getIntExtra(MOVIE_ID, 0)

            if (movieId != 0) {
                bundle.putInt(MOVIE_ID, movieId)
                navController.setGraph(R.navigation.nav_graph_details, bundle)
            } else {
                bottomNavView.isVisible = true
                navController.setGraph(R.navigation.nav_graph_list)
            }
        }
    }

    private fun initListeners(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesListFragment -> {
                    showNav()
                }
                R.id.searchFragment -> {
                    showNav()
                }
                R.id.favoriteFragment -> {
                    showNav()
                }
                R.id.settingsFragment -> {
                    showNav()
                }
                else -> {
                    hideNav()
                }
            }
        }
    }

    private fun hideNav() {
        bottomNavView.visibility = View.GONE
    }

    private fun showNav() {
        bottomNavView.visibility = View.VISIBLE
    }
}