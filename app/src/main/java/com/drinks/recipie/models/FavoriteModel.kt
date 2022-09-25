package com.drinks.recipie.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteDrinks")
class FavoriteModel(
    @ColumnInfo(name = "name") val drinkName: String,
    @ColumnInfo(name = "instructions") val drinkInstructions: String,
    @ColumnInfo(name = "alcoholic") val drinkAlcoholic: Boolean,
    @ColumnInfo(name = "thumbnail")  var drinkImage: String

) {
    @PrimaryKey(autoGenerate = true)
    var drinkId = 0

}