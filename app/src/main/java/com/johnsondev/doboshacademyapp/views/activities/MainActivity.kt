package com.johnsondev.doboshacademyapp.views.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", "onCreate ----------------------------")

        initViews()
        initListeners()
        loadData(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy ----------------------------")

        navController = null

    }

    private fun initViews(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setupWithNavController(navController!!)

    }

    private fun loadData(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            val bundle = Bundle()

            bundle.putBoolean(
                Constants.CONNECTION_ERROR_ARG,
                intent.getBooleanExtra(Constants.CONNECTION_ERROR_EXTRA, false)
            )
            val movieId = intent.getIntExtra(MOVIE_KEY, 0)

            if (movieId != 0) {
                bottomNavView.visibility = View.GONE
                bundle.putInt(MOVIE_KEY, movieId)
                navController?.setGraph(R.navigation.nav_graph_details, bundle)
            } else {
                bottomNavView.isVisible = true
                navController?.setGraph(R.navigation.nav_graph_list, bundle)
            }
        }
    }

    private fun initListeners(){
        navController?.addOnDestinationChangedListener { _, destination, _ ->
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