package com.ewersson.netflixclone_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewersson.netflixclone_app.model.Category
import com.ewersson.netflixclone_app.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {

    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.progress_main)


        if (::progress.isInitialized) {
            Log.d("Test", "ProgressBar inicializada com sucesso.")
        } else {
            Log.d("Test", "ProgressBar não foi inicializada.")
        }

        val categories = mutableListOf<Category>()

        val rv: RecyclerView = findViewById(R.id.rv_main)
        val adapter: CategoryAdapter = CategoryAdapter(categories)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val url = "https://netflix-api-lq23.onrender.com/categorie/all"
        CategoryTask(this).execute(url)

    }

    override fun onPreExecute() {
        Log.d("Test", "ProgressBar visibilidade: VISIBLE")
        progress.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        Log.d("Test", "ProgressBar visibilidade: GONE")
        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        Log.d("Test", "ProgressBar visibilidade: GONE")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progress.visibility = View.GONE
    }


}