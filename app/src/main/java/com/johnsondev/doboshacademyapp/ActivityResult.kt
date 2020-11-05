package com.johnsondev.doboshacademyapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.widget.TextView

class ActivityResult : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val fade = Fade()

        fade.excludeTarget(R.id.action_bar_container, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade

        title = "Activity 2"

        val tvName: TextView = findViewById(R.id.tv_name)
        val tvAge: TextView = findViewById(R.id.tv_age)

        intent?.let {
            val user = intent.extras?.getParcelable(TRANS) as User?
            val name = user?.name.toString()
            val age = user?.age.toString()
            tvName.text = "Имя : $name"
            tvAge.text = "Возраст : $age"
        }

    }

    companion object {
        val TRANS = "trans"
    }
}