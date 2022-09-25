package com.drinks.recipie.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.drinks.recipie.models.FavoriteModel

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favoriteModel: FavoriteModel)

    @Update
    fun update(favoriteModel: FavoriteModel)

    @Delete
   fun delete(favoriteModel: FavoriteModel)

    @Query("Select * from FavoriteDrinks order by drinkId DESC")
    fun getAllFavoriteDrinks(): LiveData<List<FavoriteModel>>

}