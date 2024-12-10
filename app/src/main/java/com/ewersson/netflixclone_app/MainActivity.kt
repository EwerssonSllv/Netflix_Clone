package com.ewersson.netflixclone_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewersson.netflixclone_app.model.Movie


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies = mutableListOf<Movie>()
        for(i in 0..6){
            val movie = Movie(R.drawable.capa_got)
            movies.add(movie)
        }

        val rv: RecyclerView = findViewById(R.id.recycler_main)
        val adapter: MainAdapter = MainAdapter(movies)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv.adapter = adapter

    }
}