package com.johnsondev.doboshacademyapp.views.movielist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorsAdapter
import com.johnsondev.doboshacademyapp.adapters.OnActorItemClickListener
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.utilities.Constants
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_HORIZONTAL
import com.johnsondev.doboshacademyapp.utilities.Constants.POP_ACTORS_SPEC_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.SPECIFIC_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.viewmodel.MoviesListViewModel

class SpecificActorsListFragment : BaseFragment() {

    private lateinit var tvSpecListType: TextView
    private lateinit var rvSpecActorsList: RecyclerView
    private lateinit var backViewGroup: View
    private lateinit var adapter: ActorsAdapter
    private lateinit var specType: String
    private lateinit var moviesListViewModel: MoviesListViewModel


    override fun initViews(view: View) {
        moviesListViewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]

        tvSpecListType = view.findViewById(R.id.spec_actors_list_type_tv)
        rvSpecActorsList = view.findViewById(R.id.rv_spec_actors_list)
        backViewGroup = view.findViewById(R.id.back_to_main_view_group)
        adapter = ActorsAdapter(requireContext(), actorClickListener, ITEM_TYPE_HORIZONTAL)
        rvSpecActorsList.adapter = adapter
        rvSpecActorsList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun layoutId(): Int = R.layout.fragment_specific_actors_list

    override fun loadData() {
        specType = arguments?.getString(SPECIFIC_LIST_TYPE) ?: getString(R.string.actors_list)
        when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                moviesListViewModel.loadPopularActors()
            }
        }
    }

    override fun bindViews(view: View) {
        tvSpecListType.text = when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                getString(R.string.popular_actors)
            }
            else -> {
                getString(R.string.actors_list)
            }
        }
    }

    override fun initListenersAndObservers(view: View) {
        backViewGroup.setOnClickListener {
            findNavController().popBackStack()
        }

        when (specType) {
            POP_ACTORS_SPEC_TYPE -> {
                moviesListViewModel.getPopularActors().observe(viewLifecycleOwner){
                    adapter.setActors(it)
                }
            }
        }
    }

    private val actorClickListener = object : OnActorItemClickListener {
        override fun onClick(actor: Actor) {
            findNavController().navigate(
               SpecificActorsListFragmentDirections.actionSpecificActorsListFragmentToActorDetailsActivity2(actor.id)
            )
        }
    }


}