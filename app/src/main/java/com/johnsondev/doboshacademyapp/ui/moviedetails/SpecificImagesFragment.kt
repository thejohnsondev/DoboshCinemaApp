package com.johnsondev.doboshacademyapp.ui.moviedetails

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ImagesAdapter
import com.johnsondev.doboshacademyapp.databinding.FragmentSpecificImagesBinding
import com.johnsondev.doboshacademyapp.utilities.Constants.BACKDROP_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.IMAGES_LIST_TYPE
import com.johnsondev.doboshacademyapp.utilities.Constants.ITEM_TYPE_POSTER
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_KEY
import com.johnsondev.doboshacademyapp.utilities.appComponent
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.observeOnce
import javax.inject.Inject

class SpecificImagesFragment : BaseFragment(R.layout.fragment_specific_images) {

    @Inject
    lateinit var factory: MovieDetailsViewModel.Factory
    private val viewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[MovieDetailsViewModel::class.java]
    }
    private val binding by viewBinding(FragmentSpecificImagesBinding::bind)
    private lateinit var movieImagesAdapter: ImagesAdapter
    private var listType: Int? = null
    private var spanCount: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun initFields() {
        listType = arguments?.getInt(IMAGES_LIST_TYPE)
        movieImagesAdapter = ImagesAdapter(requireContext(), listType!!)
        spanCount = if (listType == ITEM_TYPE_POSTER) 2 else 1
        binding.rvSpecImagesList.layoutManager = GridLayoutManager(requireContext(), spanCount ?: 1)
        binding.rvSpecImagesList.adapter = movieImagesAdapter
    }

    override fun loadData() {}

    override fun bindViews() {
        binding.specListTypeTv.text =
            if (listType == ITEM_TYPE_POSTER) getString(R.string.posters) else getString(R.string.backdropds)
    }

    override fun initListenersAndObservers() {

        binding.backToMainViewGroup.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getMovieImagesForCurrentMovie().observeOnce(this, {
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