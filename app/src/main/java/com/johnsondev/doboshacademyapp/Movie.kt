package com.johnsondev.doboshacademyapp

import androidx.annotation.DrawableRes

data class Movie(
    val id: Int,
    val name: String,
    val genre: String,
    val reviews: Int,
    val rating: Int,
    val time: Int,
    val age: Int,
    @DrawableRes
    val movieImg: Int,
    val isFavorite: Boolean
) {

    companion object {
        val movies = arrayOf(
            Movie(
                1,
                "Avengers: End Game",
                "Action, Adventure, Drama",
                125,
                8,
                137,
                13,
                R.drawable.movie_avengers_img,
                false
            ),
            Movie(
                2,
                "Tenet",
                "Action, Sci-Fi, Thriller",
                98,
                10,
                97,
                16,
                R.drawable.movie_tenet_img,
                true
            ),
            Movie(
                3,
                "Black Widow",
                "Action, Adventure, Sci-Fi",
                38,
                8,
                102,
                13,
                R.drawable.movie_blackwin_img,
                false
            ),
            Movie(
                4,
                "Wonder Woman 1984",
                "Action, Adventure, Fantasy",
                74,
                10,
                120,
                13,
                R.drawable.movie_wonder_img,
                false
            )
        )
    }

}
