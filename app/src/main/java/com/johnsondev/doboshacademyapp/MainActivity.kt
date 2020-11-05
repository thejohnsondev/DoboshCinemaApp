package com.johnsondev.doboshacademyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fade = Fade()

        fade.excludeTarget(R.id.action_bar_container, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade

        title = "Activity 1"

        val nameField: EditText? = findViewById(R.id.nameField)
        val ageField: EditText? = findViewById(R.id.ageField)
        val image: ImageView = findViewById(R.id.image1)

        val enterBtn: Button = findViewById(R.id.enterBtn)
        enterBtn.setOnClickListener(View.OnClickListener {

            val name = nameField?.text.toString()
            val age = if (ageField?.text.toString() == "") 0 else ageField?.text.toString().toInt()

            val user = User(name, age)
            val intent = Intent(this, ActivityResult::class.java)
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    image,
                    ViewCompat.getTransitionName(image).toString())
            intent.putExtra(ActivityResult.TRANS, user)
            startActivity(intent, options.toBundle())

        })

    }


}