package com.ewersson.netflixclone_app.util

import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class CategoryTask {

    fun execute(url: String, token: String) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                val requestURL = URL(url)
                val urlConnection = requestURL.openConnection() as HttpURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                urlConnection.setRequestProperty("Authorization", "Bearer $token")

                val statusCode = urlConnection.responseCode

                if (statusCode > 400) {
                    throw IOException("Communication Error! Status Code: $statusCode")
                }

                val stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() } // Bytes -> String
                Log.i("Teste", jsonAsString)

            } catch (e: IOException) {
                Log.e("Test", e.message ?: "Unknown Error!", e)
            }
        }
    }
}
