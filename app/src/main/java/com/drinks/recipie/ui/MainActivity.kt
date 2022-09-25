package com.drinks.recipie.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import com.drinks.recipie.R
import com.drinks.recipie.databinding.ActivityMainBinding
import com.drinks.recipie.fragments.Favourites
import com.drinks.recipie.fragments.Home

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewBinding: ActivityMainBinding
    var fragmentTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        fragmentTransaction = getSupportFragmentManager().beginTransaction()
        openHomeFragment()

        uiViews()

    }

    private fun uiViews() {
        viewBinding.tabs.btnFavourite.setOnClickListener(this)
        viewBinding.tabs.btnHome.setOnClickListener(this)
        //for changing colors in dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFavourite -> {
                openFavouriteFragment()
                changeFavoritesColors()
            }
            R.id.btnHome -> {
                openHomeFragment()
                changeHomeColors()
            }
        }

    }

    private fun changeFavoritesColors() {
        viewBinding.tabs.ivHome.setImageResource(R.drawable.home_outline)
        viewBinding.tabs.cardHome.setCardBackgroundColor((Color.parseColor("#FFFFFF")))
        viewBinding.tabs.ivFavorite.setImageResource(R.drawable.star_filled)
        viewBinding.tabs.cardFavorites.setCardBackgroundColor((Color.parseColor("#5EA1AF")))
        viewBinding.tabs.tvHome.setTextColor((Color.parseColor("#5EA1AF")))
        viewBinding.tabs.tvFavourite.setTextColor((Color.parseColor("#FFFFFF")))
    }

    private fun changeHomeColors() {
        viewBinding.tabs.ivFavorite.setImageResource(R.drawable.star_outline)
        viewBinding.tabs.cardFavorites.setCardBackgroundColor((Color.parseColor("#FFFFFF")))
        viewBinding.tabs.ivHome.setImageResource(R.drawable.home_filled)
        viewBinding.tabs.cardHome.setCardBackgroundColor((Color.parseColor("#5EA1AF")))
        viewBinding.tabs.tvHome.setTextColor((Color.parseColor("#FFFFFF")))
        viewBinding.tabs.tvFavourite.setTextColor((Color.parseColor("#5EA1AF")))
    }


    private fun openFavouriteFragment() {
        val textFragment = Favourites()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.frame, textFragment)
            .addToBackStack(null).commit()

    }

    private fun openHomeFragment() {
        val textFragment = Home()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(R.id.frame, textFragment)
            .addToBackStack(null).commit()

    }


    override fun onBackPressed() {
        finish()
    }
}