package com.kidris.info5126mobileproject.ui.movie


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kidris.info5126mobileproject.AboutPage
import com.kidris.info5126mobileproject.MovieViewModel
import com.kidris.info5126mobileproject.R
import com.kidris.info5126mobileproject.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    // Create a ViewModel instance using viewModels() delegate from the activity's scope
    val viewModel: MovieViewModel by viewModels()

    // Create an instance of MoviePagingAdapter for displaying paged movie data
    val movieAdapter = MoviePagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Additional setup or initialization can be performed here
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using view binding
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up the RecyclerView and other UI elements when the view is created

        // Configure the RecyclerView and its adapter
        setRecyclerView()

        // Set up the SearchView to listen for query text changes
        binding.movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // When the user submits a query, update the ViewModel with the new query
                query?.let {
                    viewModel.setQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return false
            }
        })

        // Observe the LiveData from the ViewModel to update the adapter with the latest data
        viewModel.list.observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }

        // Set an OnClickListener for the overflow icon
        binding.overflowIcon.setOnClickListener {

            val intent = Intent(requireContext(), AboutPage::class.java)
            startActivity(intent)
        }

    }

    // Helper function to set up the RecyclerView with the MoviePagingAdapter
    private fun setRecyclerView() {
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    // You can keep the onOptionsItemSelected function for handling other menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Handle other menu items if needed
            else -> return super.onOptionsItemSelected(item)
        }
    }

}











