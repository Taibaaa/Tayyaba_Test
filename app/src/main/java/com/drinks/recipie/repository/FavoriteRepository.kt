package com.drinks.recipie.repository

import androidx.lifecycle.LiveData
import com.drinks.recipie.models.FavoriteModel
import com.drinks.recipie.roomdb.FavoriteDao


class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allDrinksList: LiveData<List<FavoriteModel>> = favoriteDao.getAllFavoriteDrinks()

    suspend fun insert(favoriteModel: FavoriteModel)
    {
        favoriteDao.insertFav(favoriteModel)

    }
}

