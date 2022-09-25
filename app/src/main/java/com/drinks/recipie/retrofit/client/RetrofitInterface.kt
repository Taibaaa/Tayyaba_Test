package com.drinks.recipie.retrofit.client

import com.drinks.recipie.models.DrinkList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("search.php")
    fun searchDrinkByName(@Query("s") name: String): Call<DrinkList>

    @GET("search.php")
    fun searchDrinkByAlphabet(@Query("f") name: String): Call<DrinkList>

    @GET("filter.php?a=Alcoholic")
    fun getAlcoholicDrinks(): Call<DrinkList>
}