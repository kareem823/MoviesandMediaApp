package com.kidris.info5126mobileproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.kidris.info5126mobileproject.remote.MovieInterface
import com.kidris.info5126mobileproject.ui.movie.MoviePaging
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val movieInterface: MovieInterface) : ViewModel() {

    // MutableLiveData to hold the current query string
    private val query = MutableLiveData<String>()

    // LiveData to observe changes in the query and trigger paging updates
    val list = query.switchMap {
        Pager(PagingConfig(pageSize = 10)) {
            MoviePaging(it, movieInterface)
        }.liveData.cachedIn(viewModelScope)
    }

    // Function to set the query and update the MutableLiveData
    fun setQuery(s: String) {
        query.value = s
    }

}

