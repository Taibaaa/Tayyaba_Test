package com.drinks.recipie.models

import com.google.gson.annotations.SerializedName
import java.util.*

class DrinkList{

    @SerializedName("drinks")
    var list: MutableList<DrinkModel> = ArrayList()
}