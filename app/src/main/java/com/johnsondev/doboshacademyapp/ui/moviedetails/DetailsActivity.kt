package com.johnsondev.doboshacademyapp.ui.moviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY

class DetailsActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private val args: DetailsActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_details) as NavHostFragment
        navController = navHostFragment.navController

        val bundle = Bundle()
        bundle.putInt(MOVIE_KEY, args.movieId)


        navController?.setGraph(R.navigation.nav_graph_details, bundle)

    }

    override fun onDestroy() {
        super.onDestroy()
        navController = null
    }
}