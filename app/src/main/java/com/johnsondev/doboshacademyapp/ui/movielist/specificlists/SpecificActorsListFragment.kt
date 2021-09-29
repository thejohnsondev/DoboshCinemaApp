package com.johnsondev.doboshacademyapp.ui.movielist.specificlists

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.databinding.FragmentSpecificActorsListBinding
import com.johnsondev.doboshacademyapp.ui.movielist.MoviesListViewModel
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.Constants.POP_ACTORS_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SEARCH_RESULT_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment

class SpecificActorsListFragment : BaseFragment(R.layout.fragment_specific_actors_list) {

    private val moviesListViewModel by viewModels<MoviesListViewModel>()
    private val binding by viewBinding(FragmentSpecificActorsListBinding::bind)
    private lateinit var adapter: ActorsAdapter
    private lateinit var specType: String

    override fun initFields() {
        adapter = ActorsAdapter(requireContext(), actorClickListener, ITEM_TYPE_HORIZONTAL)
        binding.rvSpecActorsList.adapter = adapter
        binding.rvSpecActorsList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun loadData() {
        specType = arguments?.getString(SPECIFIC_LIST_TYPE) ?: getString(R.string.actors_list)
        when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                moviesListViewModel.loadPopularActors()
            }
        }
    }

    override fun bindViews() {
        binding.specActorsListTypeTv.text = when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                getString(R.string.popular_actors)
            }
            else -> {
                getString(R.string.actors_list)
            }
        }
    }

    override fun initListenersAndObservers() {
        binding.backToMainViewGroup.setOnClickListener {
            findNavController().popBackStack()
        }

        when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                moviesListViewModel.getPopularActors().observe(viewLifecycleOwner) {
                    adapter.setActors(it)
                }
            }
            SEARCH_RESULT_SPEC_TYPE -> {
                val list =
                    arguments?.getParcelableArrayList<Actor>(Constants.ACTORS_SEARCH_RESULT_SPEC_TYPE)
                adapter.setActors(list ?: emptyList())
            }
        }
    }

    private val actorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
                SpecificActorsListFragmentDirections.actionSpecificActorsListFragmentToActorDetailsActivity2(
                    actor.id
                )
            )
        }
    }
}