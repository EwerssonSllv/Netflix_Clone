package com.ewersson.netflixclone_app.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DownloadImageTask(private val callback: Callback) {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback {
        fun onResult(bitmap: Bitmap?)
    }

    fun execute(url: String) {
        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {
                val requestURL = URL(url)
                urlConnection = requestURL.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 10000
                urlConnection.connectTimeout = 10000

                val statusCode = urlConnection.responseCode
                if (statusCode > 400) {
                    throw IOException("Communication Error! Status Code: $statusCode")
                }

                stream = urlConnection.inputStream
                val bitmap = BitmapFactory.decodeStream(stream)

                handler.post {
                    callback.onResult(bitmap)
                }
            } catch (e: IOException) {
                Log.e("DownloadImageTask", "Error: ${e.message}")
                handler.post {
                    callback.onResult(null)
                }
            } finally {
                urlConnection?.disconnect()
                stream?.close()
            }
        }
    }
}
