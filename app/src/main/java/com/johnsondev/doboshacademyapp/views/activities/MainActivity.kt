package com.johnsondev.doboshacademyapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johnsondev.doboshacademyapp.R
import com.johnsondev.doboshacademyapp.views.movielist.FragmentMoviesList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_container, FragmentMoviesList())
                commit()
            }
        }
    }
}