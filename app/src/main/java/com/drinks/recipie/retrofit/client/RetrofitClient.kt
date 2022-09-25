package com.drinks.recipie.retrofit.client

import com.drinks.recipie.models.DrinkList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    private lateinit var retrofit:Retrofit
    private lateinit var retrofitInterface: RetrofitInterface

    init{
        createRetrofit(createGson())
        createAPI()
    }

    private fun createRetrofit(gson: Gson){
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    private fun createGson(): Gson{
        return GsonBuilder().setLenient().create()
    }

    private fun createAPI() {
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }

    fun searchDrinkByName(name: String): Call<DrinkList>{
        return retrofitInterface.searchDrinkByName(name)
    }

    fun searchDrinkByAlphabet(name: String): Call<DrinkList> {
        return retrofitInterface.searchDrinkByAlphabet(name)
    }

    fun getAlcoholicDrinksCall():Call<DrinkList>{
        return retrofitInterface.getAlcoholicDrinks()
    }

}