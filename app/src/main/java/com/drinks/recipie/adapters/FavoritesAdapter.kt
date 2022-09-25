package com.drinks.recipie.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drinks.recipie.R
import com.drinks.recipie.models.FavoriteModel
import java.util.*

class FavoritesAdapter(
    val context: Context
) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val allFavourite = ArrayList<FavoriteModel>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkTitle: TextView = itemView.findViewById(R.id.drinkName)
        val drinkInstructions: TextView = itemView.findViewById(R.id.tv_drink_instruction)
        val drinkThumbnail: ImageView = itemView.findViewById(R.id.drinkImage)
        val drinkAlcohol: CheckBox = itemView.findViewById(R.id.cbAlcoholic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //we will be inflating our layout here
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.favourites_item,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val drinkData = allFavourite.get(position)

        holder.drinkTitle.setText(drinkData.drinkName)
        holder.drinkInstructions.setText(drinkData.drinkInstructions)
        Glide.with(context).load(drinkData.drinkImage).into(holder.drinkThumbnail)
        holder.drinkAlcohol.isChecked = drinkData.drinkAlcoholic ==true

    }

    override fun getItemCount(): Int {
        return allFavourite.size
    }

    fun setDrinks(newList: List<FavoriteModel>) {
        allFavourite.clear()
        allFavourite.addAll(newList)
        notifyDataSetChanged()
    }
}
