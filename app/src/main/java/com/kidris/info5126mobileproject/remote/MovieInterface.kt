package com.kidris.info5126mobileproject.remote

import com.kidris.info5126mobileproject.data.MovieResponse
import com.kidris.info5126mobileproject.data.moviedetails.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {


    @GET("/")
    suspend fun getAllMovies(
        @Query("s")s:String,
        @Query("page")page:Int,
        @Query("apiKey")apiKey:String
    ):Response<MovieResponse>


    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbid:String,
        @Query("apiKey")apiKey:String
    ):Response<MovieDetails>


}