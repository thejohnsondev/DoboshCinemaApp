package com.johnsondev.doboshacademyapp.views.actordetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.utilities.Constants

class ActorDetailsActivity : AppCompatActivity() {


    private var navController: NavController? = null
    private val args: ActorDetailsActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_details)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_actor_details) as NavHostFragment
        navController = navHostFragment.navController

        val bundle = Bundle()
        bundle.putInt(Constants.ACTOR_KEY, args.actorId)

        navController?.setGraph(R.navigation.nav_graph_actor_details, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController = null
    }
}