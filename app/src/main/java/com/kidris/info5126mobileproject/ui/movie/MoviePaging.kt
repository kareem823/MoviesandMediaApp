package com.kidris.info5126mobileproject.ui.movie

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kidris.info5126mobileproject.data.Movie
import com.kidris.info5126mobileproject.remote.MovieInterface
import com.kidris.info5126mobileproject.utils.Constants


class MoviePaging(val s: String, val movieInterface: MovieInterface) : PagingSource<Int, Movie>() {
    // Retrieves the refresh key for the PagingSource based on the current state
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // Find the closest page to the anchor position
            val anchorPage = state.closestPageToPosition(anchorPosition)

            // Calculate the refresh key using the prevKey or nextKey of the anchor page
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    // Loads a page of data for the PagingSource
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        // Retrieve the requested page, default to the first page if key is null
        val page = params.key ?: 1

        return try {
            // Fetch movie data from the remote data source using the provided query and API key
            val data = movieInterface.getAllMovies(s, page, Constants.API_KEY)
            Log.d("TAG", "load: ${data.body()}")

            // Create a LoadResult.Page with the fetched data, prevKey, and nextKey
            LoadResult.Page(
                data = data.body()?.Search.orEmpty(),  // Use an empty list if data is null
                prevKey = if (page == 1) null else page - 1,  // Set prevKey to null if it's the first page
                nextKey = if (data.body()?.Search?.isEmpty()!!) null else page + 1  // Set nextKey to null if there are no more pages
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}
