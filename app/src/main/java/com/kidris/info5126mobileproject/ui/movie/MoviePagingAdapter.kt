package com.kidris.info5126mobileproject.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kidris.info5126mobileproject.R
import com.kidris.info5126mobileproject.data.Movie
import com.kidris.info5126mobileproject.databinding.ViewHolderMovieBinding

class MoviePagingAdapter : PagingDataAdapter<Movie, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    // DiffUtil.ItemCallback for efficient RecyclerView updates based on Movie data
    companion object {
        // DIFF_UTIL is a static property that defines how to compare Movie items for efficient updates
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            // Checks if the unique identifiers of two items are the same
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            // Checks if the contents of two items are the same
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    // ViewHolder class that holds a reference to the binding object for a Movie item
    inner class MyViewHolder(val viewDataBinding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    // Binds data to the ViewHolder when it is displayed in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Retrieve the Movie item at the given position
        val movie = getItem(position)

        // Bind the Movie item to the ViewHolder's data binding object
        holder.viewDataBinding.movie = movie

        // Ensure that pending bindings are executed immediately
        holder.viewDataBinding.executePendingBindings()
    }

    // Inflates the layout and creates a new ViewHolder when needed by the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflates the layout using the generated binding class ViewHolderMovieBinding
        val binding = ViewHolderMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        // Returns a new ViewHolder with the inflated binding object
        return MyViewHolder(binding)
    }

}