package com.drinks.recipie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.drinks.recipie.models.DrinkModel
import com.drinks.recipie.models.FavoriteModel
import com.drinks.recipie.repository.DrinksRepository
import com.drinks.recipie.repository.FavoriteRepository
import com.drinks.recipie.roomdb.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DrinksRepository = DrinksRepository(application)
    val favRepository: FavoriteRepository
    val allDrinks: LiveData<List<FavoriteModel>>


    fun getDrinkList(query: String): MutableLiveData<List<DrinkModel>> {
        return repository.getDrinkList(query)
    }

    fun getDrinkListByAlphabet(query: String): MutableLiveData<List<DrinkModel>> {
        return repository.getDrinkListbyAlphabet(query)
    }

    init {
        val dao = FavoriteDatabase.getDatabase(application).getFavoriteDao()
        favRepository = FavoriteRepository(dao)
        allDrinks = favRepository.allDrinksList
    }

    fun addToFavourites(favoriteModel: FavoriteModel) = viewModelScope.launch(Dispatchers.IO) {
        favRepository.insert(favoriteModel)

    }
}