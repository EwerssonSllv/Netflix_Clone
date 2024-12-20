package com.ewersson.netflixclone_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewersson.netflixclone_app.model.Category
import com.ewersson.netflixclone_app.util.CategoryTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()

        val rv: RecyclerView = findViewById(R.id.rv_main)
        val adapter: CategoryAdapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val url = "http://127.0.0.1:8081/categories/all"
        val token = "Your_Token" // Token By Login
        CategoryTask().execute(url, token)

    }
}