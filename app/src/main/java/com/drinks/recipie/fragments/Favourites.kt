package com.drinks.recipie.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drinks.recipie.R
import com.drinks.recipie.adapters.DrinksAdapter
import com.drinks.recipie.adapters.FavoritesAdapter
import com.drinks.recipie.databinding.FragmentFavouritesBinding
import com.drinks.recipie.util.ViewModelFragment
import com.drinks.recipie.viewmodel.ViewModel


class Favourites : ViewModelFragment() {

    private lateinit var viewBinding: FragmentFavouritesBinding
    private lateinit var favouritesAdapter: FavoritesAdapter
    lateinit var viewModel: ViewModel

    override fun viewModel() {
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        loadFavourites()
    }

    private fun loadFavourites() {

        val drinksRv: RecyclerView = viewBinding.root.findViewById(R.id.rvFavorites)
        drinksRv.layoutManager = LinearLayoutManager(activity)

        viewModel.favRepository.allDrinksList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewBinding.noDataAnimation.visibility = View.VISIBLE
                viewBinding.tvNoData.visibility = View.VISIBLE

            } else {
                viewBinding.noDataAnimation.visibility = View.GONE
                viewBinding.tvNoData.visibility = View.GONE
            }
            favouritesAdapter.setDrinks(it)
        }

        favouritesAdapter = FavoritesAdapter(context as Activity)
        drinksRv.setAdapter(favouritesAdapter)
    }

    private fun setupViewModel() {
        viewModel()

    }

}