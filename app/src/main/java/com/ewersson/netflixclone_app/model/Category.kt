package com.ewersson.netflixclone_app.model

data class Category(
    val id: Int,
    val name: String,
    val movies: List<Movie>
)