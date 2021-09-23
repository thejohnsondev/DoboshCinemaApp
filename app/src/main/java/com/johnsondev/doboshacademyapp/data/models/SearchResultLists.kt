package com.johnsondev.doboshacademyapp.data.models

import com.johnsondev.doboshacademyapp.data.models.base.Actor
import com.johnsondev.doboshacademyapp.data.models.base.Movie

data class SearchResultLists(
    val movies: List<Movie>,
    val actors: List<Actor>
)