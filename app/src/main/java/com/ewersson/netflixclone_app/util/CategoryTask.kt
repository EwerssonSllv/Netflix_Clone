package com.ewersson.netflixclone_app.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ewersson.netflixclone_app.model.Category
import com.ewersson.netflixclone_app.model.Movie
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())

    interface Callback {

        fun onPreExecute()

        fun onResult(categories: List<Category>)

        fun onFailure(message: String)

    }

    fun execute(url: String) {
        (callback as Activity).runOnUiThread {
            callback.onPreExecute()
        }

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var buffer: BufferedInputStream? = null
            var stream: InputStream? = null
            try {
                val requestURL = URL(url)
                urlConnection = requestURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                val statusCode = urlConnection.responseCode

                if (statusCode > 400) {
                    throw IOException("Communication Error! Status Code: $statusCode")
                }

                stream = urlConnection.inputStream
                buffer = BufferedInputStream(stream)
                val jsonAsString = toString(buffer)

                // Aqui você usa toCategories corretamente
                val categories = toCategories(jsonAsString)

                Log.i("Test", categories.toString())

                handler.post {
                    (callback as Activity).runOnUiThread {
                        callback.onResult(categories)
                    }
                }

            } catch (e: IOException) {
                val message = e.message ?: "Unknown Error!"
                Log.e("Test", e.message ?: "Unknown Error!", e)

                handler.post {
                    (callback as Activity).runOnUiThread {
                        callback.onFailure(message)
                    }
                }

            } finally {
                urlConnection?.disconnect()
                stream?.close()
                buffer?.close()
            }
        }
    }

    private fun toCategories(jsonAsString: String): List<Category> {
        val categories = mutableListOf<Category>()
        val jsonArray = JSONArray(jsonAsString)

        for (i in 0 until jsonArray.length()) {
            val jsonCategory = jsonArray.getJSONObject(i)

            val title = jsonCategory.getString("name")

            val jsonMovies = jsonCategory.getJSONArray("movies")
            val movies = mutableListOf<Movie>()

            for (j in 0 until jsonMovies.length()) {
                val jsonMovie = jsonMovies.getJSONObject(j)
                val movieTitle = jsonMovie.getString("title")
                val image = jsonMovie.getString("image")
                val cover = jsonMovie.getString("cover")
                val description = jsonMovie.getString("description")
                val cast = jsonMovie.getString("cast")

                movies.add(Movie(0, movieTitle, image, cover, description, cast))
            }

            categories.add(Category(0, title, movies))
        }

        return categories
    }


    private fun toString(stream: InputStream): String {
        val bytes = ByteArray(1024)
        val byteAOPS = ByteArrayOutputStream()
        var read: Int
        do {
            read = stream.read(bytes)
            if (read <= 0) {
                break
            }

            byteAOPS.write(bytes, 0, read)

        } while (true)

        return String(byteAOPS.toByteArray())
    }
}

