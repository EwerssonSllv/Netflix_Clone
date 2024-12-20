package com.ewersson.netflixclone_app.model

data class Movie(
    val id:Int,
    var title: String,
    var image: String,
    var cover: String,
    var description: String,
    var cast: String,
    var category: Category? = null
)