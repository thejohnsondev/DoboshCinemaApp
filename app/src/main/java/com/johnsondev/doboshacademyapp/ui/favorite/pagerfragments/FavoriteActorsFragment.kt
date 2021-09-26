package com.johnsondev.doboshacademyapp.ui.favorite.pagerfragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModel
import com.johnsondev.doboshacademyapp.ui.favorite.FavoriteFragmentDirections

class FavoriteActorsFragment : BaseFragment() {


    private val favoritesViewModel by viewModels<FavoritesViewModel>()
    private lateinit var rvActors: RecyclerView
    private lateinit var actorsAdapter: ActorsAdapter
    private lateinit var loadingIndicator: ProgressBar

    override fun initViews(view: View) {

        rvActors = view.findViewById(R.id.rv_movie_actors)
        loadingIndicator = view.findViewById(R.id.movie_actors_loading_indicator)
        loadingIndicator.visibility = View.VISIBLE
        actorsAdapter = ActorsAdapter(
            requireContext(), actorClickListener,
            ITEM_TYPE_HORIZONTAL
        )
        rvActors.layoutManager = LinearLayoutManager(requireContext())
        rvActors.adapter = actorsAdapter
    }

    override fun layoutId(): Int = R.layout.fragment_movie_details_actors

    override fun loadData() {}

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {

        favoritesViewModel.getFavoriteActors().observeOnce(viewLifecycleOwner, {
            loadingIndicator.visibility = View.GONE
            rvActors.visibility = View.VISIBLE
            actorsAdapter.setActors(it)
        })

        favoritesViewModel.actorsLoadingState.observeOnce(viewLifecycleOwner, {
            when (it) {
                is Loading -> {
                    rvActors.visibility = View.GONE
                    loadingIndicator.visibility = View.VISIBLE
                }
            }
        })

    }

    private val actorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            val bundleWithActor = Bundle()
            bundleWithActor.putParcelable(Constants.ACTOR_KEY, actor)
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToActorDetailsActivity(actor.id)
            )
        }

    }


}