package com.drinks.recipie.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.drinks.recipie.models.DrinkModel
import com.drinks.recipie.models.DrinkList
import com.drinks.recipie.retrofit.client.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DrinksRepository(context: Context) {

    private var drinkModel: MutableLiveData<DrinkModel> = MutableLiveData()

    private var drinkModelList: MutableLiveData<List<DrinkModel>> = MutableLiveData()

    private val mainController: RetrofitClient = RetrofitClient()

    private val updateDrinkListCallback = object : Callback<DrinkList?> {
        override fun onResponse(call: Call<DrinkList?>, response: Response<DrinkList?>) {
            if (response.isSuccessful) {
                updateDrinkList(response.body()?.list)
            } else {
                Toast.makeText(context, "Nothing Found", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<DrinkList?>, t: Throwable) {
            t.printStackTrace()
        }
    }

    fun getDrinkList(query: String): MutableLiveData<List<DrinkModel>> {

        val callback: Call<DrinkList> = when (query) {
            "alcohol" -> mainController.getAlcoholicDrinksCall()
            else -> return searchDrink(query)
        }

        callback.enqueue(updateDrinkListCallback)

        return drinkModelList
    }

    fun getDrinkListbyAlphabet(query: String): MutableLiveData<List<DrinkModel>> {

        val callback: Call<DrinkList> = when (query) {
            "alcohol" -> mainController.getAlcoholicDrinksCall()
            else -> return searchDrinkByAlpha(query)
        }

        callback.enqueue(updateDrinkListCallback)

        return drinkModelList
    }


    private fun searchDrink(name: String): MutableLiveData<List<DrinkModel>> {
        mainController.searchDrinkByName(name).enqueue(updateDrinkListCallback)
        return drinkModelList
    }

    private fun searchDrinkByAlpha(name: String): MutableLiveData<List<DrinkModel>> {
        mainController.searchDrinkByAlphabet(name).enqueue(updateDrinkListCallback)
        return drinkModelList
    }

    private fun updateDrinkList(drinkModelList: MutableList<DrinkModel>?) {

        try {
            if (!drinkModelList!!.isEmpty()) {
                this.drinkModelList.postValue(drinkModelList)
            } else {
                this.drinkModelList.postValue(ArrayList())
                /* Toast.makeText(withContext(this), "Record not Found", Toast.LENGTH_SHORT).show()*/

            }
        } catch (e: Exception) {
            Log.e("TAG", "nothing found: $e")
        }

    }

    private fun updateDrink(drinkModelList: MutableList<DrinkModel>?) {
        if (drinkModelList?.isEmpty() == false) {
            this.drinkModel.postValue(drinkModelList[0])
        } else {
            this.drinkModel.postValue(DrinkModel())
        }
    }

}