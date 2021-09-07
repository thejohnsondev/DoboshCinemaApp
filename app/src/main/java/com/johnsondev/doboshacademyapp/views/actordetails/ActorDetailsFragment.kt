package com.johnsondev.doboshacademyapp.views.actordetails

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.adapters.ActorImagesAdapter
import com.johnsondev.doboshacademyapp.adapters.MoviesAdapter
import com.johnsondev.doboshacademyapp.adapters.OnImageClickListener
import com.johnsondev.doboshacademyapp.adapters.OnRecyclerItemClicked
import com.johnsondev.doboshacademyapp.data.models.Actor
import com.johnsondev.doboshacademyapp.data.models.Movie
import com.johnsondev.doboshacademyapp.data.network.dto.ActorImageProfileDto
import com.johnsondev.doboshacademyapp.utilities.Constants.ACTOR_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.MOVIE_KEY
import com.johnsondev.doboshacademyapp.utilities.Constants.POSTER_PATH
import com.johnsondev.doboshacademyapp.utilities.animateView
import com.johnsondev.doboshacademyapp.utilities.base.BaseFragment
import com.johnsondev.doboshacademyapp.utilities.showMessage
import com.johnsondev.doboshacademyapp.viewmodel.MovieDetailsViewModel
import com.johnsondev.doboshacademyapp.views.movielist.MoviesListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ActorDetailsFragment : BaseFragment() {

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var tvName: TextView
    private lateinit var tvBirthDay: TextView
    private lateinit var tvDeathDay: TextView
    private lateinit var tvPlaceOfBirth: TextView
    private lateinit var tvBiography: TextView
    private lateinit var tvImagesCount: TextView
    private lateinit var tvMoviesCount: TextView
    private lateinit var ivPosterProfile: ImageView
    private lateinit var backToMovieDetailsBtn: View
    private lateinit var birthDayView: TextView
    private lateinit var deathDayView: TextView
    private lateinit var placeOfBirthView: TextView
    private lateinit var rvActorDetailsMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var rvActorImages: RecyclerView
    private lateinit var actorImagesAdapter: ActorImagesAdapter
    private lateinit var fragmentBackgroundLayout: View
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var currentActor: Actor? = null


    override fun initViews(view: View) {

        detailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        tvName = view.findViewById(R.id.tv_actor_name_detail)
        tvBirthDay = view.findViewById(R.id.tv_birth_day)
        tvPlaceOfBirth = view.findViewById(R.id.tv_place_of_birth)
        tvDeathDay = view.findViewById(R.id.tv_death_day)
        tvBiography = view.findViewById(R.id.tv_biography)
        tvImagesCount = view.findViewById(R.id.tv_images_count)
        tvMoviesCount = view.findViewById(R.id.tv_movies_count)
        ivPosterProfile = view.findViewById(R.id.iv_actor_profile_poster)
        backToMovieDetailsBtn = view.findViewById(R.id.back_to_detail_view_group)

        birthDayView = view.findViewById(R.id.birth_day)
        deathDayView = view.findViewById(R.id.death_day)
        placeOfBirthView = view.findViewById(R.id.place_of_birth)
        rvActorDetailsMovies = view.findViewById(R.id.rv_actor_details_movies)
        moviesAdapter = MoviesAdapter(view.context, movieClickListener, true)
        rvActorDetailsMovies.adapter = moviesAdapter

        rvActorImages = view.findViewById(R.id.rv_actor_images)
        actorImagesAdapter = ActorImagesAdapter(requireContext(), imageClickListener)
        rvActorImages.adapter = actorImagesAdapter

        fragmentBackgroundLayout = view.findViewById(R.id.actor_details_background)



    }

    override fun layoutId(): Int = R.layout.fragment_actor_details

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadData() {
        currentActor = arguments?.getParcelable(ACTOR_KEY)

        if (detailsViewModel.checkInternetConnection(requireContext())) {
            scope.launch {
                if (currentActor?.id != 0) {
                    detailsViewModel.loadActorDetailsById(currentActor?.id!!)
                }
            }
        } else {
            showMessage(getString(R.string.internet_connection_error))
        }
        val imagePath = "${POSTER_PATH}${currentActor?.picture}"

        detailsViewModel.calculateAverageColor(imagePath, requireContext())
    }

    override fun bindViews(view: View) {}

    override fun initListenersAndObservers(view: View) {
        tvBiography.setOnClickListener {
            when (tvBiography.maxLines) {
                15 -> {
                    animateView(tvBiography, "maxLines", 1000, 15f, 100f)
                        .start()
                }
                else -> {
                    animateView(tvBiography, "maxLines", 1000, 100f, 15f)
                        .start()
                }
            }
        }

        backToMovieDetailsBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        detailsViewModel.getActorDetails().observe(viewLifecycleOwner) {

            tvName.text = it.name
            tvBirthDay.text = it.birthDay
            tvDeathDay.text = it.deathDay
            tvPlaceOfBirth.text = it.placeOfBirth
            tvBiography.text = it.biography

            if (it.deathDay == null) deathDayView.isVisible = false
            if (it.birthDay == null) birthDayView.isVisible = false
            if (it.placeOfBirth == null) placeOfBirthView.isVisible = false

            val imagePath = "$POSTER_PATH${it.profilePath}"

            ivPosterProfile.clipToOutline = true

            ivPosterProfile.load(imagePath) {
                memoryCachePolicy(CachePolicy.ENABLED)
                crossfade(true)
                placeholder(R.drawable.ic_baseline_person_24)
                error(R.drawable.ic_baseline_person_24)
            }

        }

        detailsViewModel.getActorMovieCredits().observe(viewLifecycleOwner) {
            moviesAdapter.setMovies(it.sortedByDescending { element -> element.numberOfRatings })
            tvMoviesCount.text = it.size.toString()
        }

        detailsViewModel.getActorImages().observe(viewLifecycleOwner) {
            actorImagesAdapter.setActorImages(it)
            tvImagesCount.text = it.size.toString()
        }

        detailsViewModel.getAverageColorBody().observe(viewLifecycleOwner) {
            fragmentBackgroundLayout.background = it.toDrawable()
            activity?.window?.statusBarColor = it
            animateView(fragmentBackgroundLayout, "alpha", 750, 1f).start()
        }

        detailsViewModel.getAverageColorText().observe(viewLifecycleOwner) {
            birthDayView.setTextColor(it)
            deathDayView.setTextColor(it)
            placeOfBirthView.setTextColor(it)
            tvBiography.setTextColor(it)
            tvImagesCount.setTextColor(it)
            tvMoviesCount.setTextColor(it)
        }

        detailsViewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                onError(it)
                birthDayView.isVisible = false
                deathDayView.isVisible = false
                placeOfBirthView.isVisible = false
                tvBiography.isVisible = false
                tvImagesCount.isVisible = false
                tvMoviesCount.isVisible = false
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        detailsViewModel.clearActorDetails()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.main_color)
    }

    private val movieClickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            doOnMovieClick(movie)
        }
    }

    private val imageClickListener = object : OnImageClickListener {
        override fun onClick(actorImage: ActorImageProfileDto) {
            doOnImageClick(actorImage)
        }
    }

    private fun doOnImageClick(actorImage: ActorImageProfileDto) {
        Toast.makeText(requireContext(), actorImage.imagePath, Toast.LENGTH_SHORT).show()
    }

    private fun doOnMovieClick(movie: Movie) {
        findNavController().navigate(
            ActorDetailsFragmentDirections.actionActorDetailsFragmentToDetailsActivity(movie.id)
        )
    }
}