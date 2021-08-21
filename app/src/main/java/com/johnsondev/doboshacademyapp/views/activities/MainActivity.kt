package com.johnsondev.doboshacademyapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_ID
import com.johnsondev.doboshacademyapp.views.moviedetails.MoviesDetailsFragment
import com.johnsondev.doboshacademyapp.views.movielist.MoviesListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val bundle = Bundle()

            bundle.putBoolean(
                Constants.CONNECTION_ERROR_ARG,
                intent.getBooleanExtra(Constants.CONNECTION_ERROR_EXTRA, false)
            )
            val movieId = intent.getIntExtra(MOVIE_ID, 0)

            if (movieId != 0) {
                bundle.putInt(MOVIE_ID, movieId)
                openNextFragment(MoviesDetailsFragment(), bundle)
            } else {
                openNextFragment(MoviesListFragment(), bundle)
            }
        }
    }

    private fun openNextFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, fragment)
            commit()
        }
    }
}