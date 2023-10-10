package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_movie_details.textOverview

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Retrieve movie details from the intent
        val movieTitle = intent.getStringExtra("title")
        val backdrop = intent.getStringExtra("backdrop_path")
        val movieOverview = intent.getStringExtra("overview")
        val airDate = intent.getStringExtra("airDate")


        // Set the title and overview in TextViews
        textTitle.text = movieTitle
        textOverview.text = movieOverview
        air_date.text = "Release Date: $airDate"

        val posterImageView = findViewById<View>(R.id.imagePoster) as ImageView

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${backdrop}")
            .centerInside()
            .into(posterImageView)
    }
}
