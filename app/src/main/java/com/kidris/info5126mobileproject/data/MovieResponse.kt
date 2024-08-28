package com.kidris.info5126mobileproject.data

data class MovieResponse(
    val Response: String,
    val Search: List<Movie>,
    val totalResults: String
)