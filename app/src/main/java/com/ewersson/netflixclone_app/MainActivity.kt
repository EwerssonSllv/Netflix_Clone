package com.ewersson.netflixclone_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewersson.netflixclone_app.model.Category
import com.ewersson.netflixclone_app.model.Movie


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()
        for (j in 1..10) {
            val movies = mutableListOf<Movie>()
            for (i in 1..15) {
                val movie = Movie(R.drawable.capa_got)
                movies.add(movie)
            }
            val category = Category("Category $j", movies)
            categories.add(category)
        }

        val rv: RecyclerView = findViewById(R.id.rv_main)
        val adapter: CategoryAdapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

    }
}