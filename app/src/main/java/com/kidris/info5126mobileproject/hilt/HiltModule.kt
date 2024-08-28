package com.kidris.info5126mobileproject.hilt

import com.kidris.info5126mobileproject.remote.MovieInterface
import com.kidris.info5126mobileproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object HiltModules {

    // Provides a singleton instance of the MovieInterface using Retrofit
    // The @Singleton annotation ensures that a single instance is created and shared across the application
    @Provides
    @Singleton
    fun provideRetrofitInterface(): MovieInterface =
        // Configure Retrofit to build an instance of MovieInterface
        Retrofit.Builder()
            // Set the base URL for the MovieInterface
            .baseUrl(Constants.BASE_URL)
            // Add GsonConverterFactory for parsing JSON responses
            .addConverterFactory(GsonConverterFactory.create())
            // Build the Retrofit instance
            .build()
            .create(MovieInterface::class.java)
}