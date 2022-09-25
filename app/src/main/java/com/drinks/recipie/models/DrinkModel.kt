package com.drinks.recipie.models

import com.google.gson.annotations.SerializedName

class DrinkModel{

    @SerializedName("idDrink")
    var id: Long = 0

    @SerializedName("strDrink")
    var name: String? = ""

    @SerializedName("strInstructions")
    var instruction: String? = ""

    @SerializedName("strAlcoholic")
    var alcoholic: String? = ""

    @SerializedName("strDrinkThumb")
    var image: String? = ""

    var changeFavIcon: Boolean = false
}