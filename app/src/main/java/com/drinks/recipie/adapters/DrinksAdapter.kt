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
import com.drinks.recipie.models.DrinkModel
import java.util.*


class DrinksAdapter(
    val context: Context,
    val itemClickInterface: ItemClickInterface
) :
    RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {

    private val allDrinks = ArrayList<DrinkModel>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkTitle: TextView = itemView.findViewById(R.id.drinkName)
        val drinkInstructions: TextView = itemView.findViewById(R.id.tv_drink_instruction)
        val drinkThumbnail: ImageView = itemView.findViewById(R.id.drinkImage)
        val drinkAlcohol: CheckBox = itemView.findViewById(R.id.cbAlcoholic)
        val star: ImageView = itemView.findViewById(R.id.favourite)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //we will be inflating our layout here
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recipie_items,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val drinkData = allDrinks.get(position)

        holder.drinkTitle.setText(drinkData.name)
        holder.drinkInstructions.setText(drinkData.instruction)
        Glide.with(context).load(drinkData.image).into(holder.drinkThumbnail)
        holder.drinkAlcohol.isChecked = drinkData.alcoholic =="Alcoholic"

        if(drinkData.changeFavIcon){
            holder.star.setImageResource(R.drawable.grey_star_outline)
        }
        else{

            holder.star.setImageResource(R.drawable.ic_favorite_icon)
        }

        holder.star.setOnClickListener {
            itemClickInterface.onItemClick(drinkData,position)

        }

    }

    override fun getItemCount(): Int {
        return allDrinks.size
    }

    fun setDrinks(newList: List<DrinkModel>) {
        allDrinks.clear()
        allDrinks.addAll(newList)
        notifyDataSetChanged()
    }
}

interface ItemClickInterface {
    fun onItemClick(drinkModel: DrinkModel, position: Int)
}
