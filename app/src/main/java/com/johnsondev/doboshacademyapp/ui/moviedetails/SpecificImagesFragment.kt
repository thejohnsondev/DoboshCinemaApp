package com.johnsondev.doboshacademyapp.ui.moviedetails

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ImagesAdapter
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.IMAGES_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_POSTER
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce

class SpecificImagesFragment : BaseFragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var backBtnView: View
    private lateinit var rvMovieImages: RecyclerView
    private lateinit var movieImagesAdapter: ImagesAdapter
    private lateinit var tvListType: TextView
    private var listType: Int? = null
    private var spanCount: Int? = null

    override fun initViews(view: View) {

        listType = arguments?.getInt(IMAGES_LIST_TYPE)

        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        backBtnView = view.findViewById(R.id.back_to_main_view_group)
        rvMovieImages = view.findViewById(R.id.rv_spec_images_list)
        movieImagesAdapter = ImagesAdapter(requireContext(), listType!!)
        tvListType = view.findViewById(R.id.spec_list_type_tv)
        spanCount = if (listType == ITEM_TYPE_POSTER) 2 else 1
        rvMovieImages.layoutManager = GridLayoutManager(requireContext(), spanCount!!)
        rvMovieImages.adapter = movieImagesAdapter

    }

    override fun layoutId(): Int = R.layout.fragment_specific_images

    override fun loadData() {

    }

    override fun bindViews(view: View) {
        tvListType.text =
            if (listType == ITEM_TYPE_POSTER) getString(R.string.posters) else getString(R.string.backdropds)
    }

    override fun initListenersAndObservers(view: View) {

        backBtnView.setOnClickListener {
            findNavController().popBackStack()
        }

        detailsViewModel.getMovieImagesForCurrentMovie().observeOnce(this, {

            when (listType) {
                ITEM_TYPE_POSTER -> {
                    movieImagesAdapter.setImagesList(it[POSTER_KEY] ?: emptyList())
                }
                else -> {
                    movieImagesAdapter.setImagesList((it[BACKDROP_KEY] ?: emptyList()))
                }
            }
        })
    }
}