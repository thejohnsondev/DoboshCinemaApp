package com.johnsondev.doboshacademyapp.ui.favorite.pagerfragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.databinding.FragmentFavoriteActorsBinding
import com.johnsondev.doboshacademyapp.ui.favorite.FavoriteFragmentDirections
import com.johnsondev.doboshacademyapp.ui.favorite.FavoritesViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import com.johnsondev.doboshacademyapp.utilities.states.Loading
import javax.inject.Inject

class FavoriteActorsFragment : BaseFragment(R.layout.fragment_favorite_actors) {

    @Inject
    lateinit var factory: FavoritesViewModel.Factory
    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[FavoritesViewModel::class.java]
    }
    private val binding by viewBinding(FragmentFavoriteActorsBinding::bind)
    private lateinit var actorsAdapter: ActorsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun initFields() {
        with(binding) {
            favoriteActorsLoadingIndicator.visibility = View.VISIBLE
            actorsAdapter = ActorsAdapter(
                requireContext(), actorClickListener,
                ITEM_TYPE_HORIZONTAL
            )
            rvFavoriteActorsList.layoutManager = LinearLayoutManager(requireContext())
            rvFavoriteActorsList.adapter = actorsAdapter
        }

    }

    override fun loadData() {}

    override fun bindViews() {}

    override fun initListenersAndObservers() {
        with(binding) {
            viewModel.getFavoriteActors().observeOnce(viewLifecycleOwner, {
                favoriteActorsLoadingIndicator.visibility = View.GONE
                rvFavoriteActorsList.visibility = View.VISIBLE
                actorsAdapter.setActors(it)
            })

            viewModel.actorsLoadingState.observeOnce(viewLifecycleOwner, {
                when (it) {
                    is Loading -> {
                        rvFavoriteActorsList.visibility = View.GONE
                        favoriteActorsLoadingIndicator.visibility = View.VISIBLE
                    }
                }
            })
        }
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