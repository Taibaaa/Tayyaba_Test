package com.drinks.recipie.fragments

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drinks.recipie.R
import com.drinks.recipie.adapters.DrinksAdapter
import com.drinks.recipie.adapters.ItemClickInterface
import com.drinks.recipie.databinding.FragmentHomeBinding
import com.drinks.recipie.models.DrinkModel
import com.drinks.recipie.models.FavoriteModel
import com.drinks.recipie.util.ViewModelFragment
import com.drinks.recipie.viewmodel.ViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.launch

class Home : ViewModelFragment(), View.OnClickListener, ItemClickInterface {

    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var drinksAdapter: DrinksAdapter
    lateinit var viewModel: ViewModel
    var query: String? = null
    var queryAlpha: String? = null
    var alcohol = false

    override fun viewModel() {
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        uiViews()


        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query1: String?): Boolean {
                Log.e("text", query1!!)

                if (viewBinding.radio1.isChecked) {
                    query = query1
                    Prefs.putString("queryByName", query)

                    viewBinding.radio1.setTextColor(Color.parseColor("#FF000000"))
                    observeDrinks()
                } else {
                    queryAlpha = query1
                    Prefs.putString("queryByAlpha", queryAlpha)
                    observeDrinksByAlphabet()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("text", newText!!)
                return true
            }
        })
    }

    private fun uiViews() {
        viewBinding.radio1.setOnClickListener(this)
        viewBinding.radio2.setOnClickListener(this)

        query = Prefs.getString("queryByName", "margarita")
        queryAlpha = Prefs.getString("queryByAlpha", "t")

        if (viewBinding.radio1.isChecked) {
            viewBinding.searchView.queryHint = query
            viewBinding.radio1.setTextColor(Color.parseColor("#FF000000"))
            observeDrinks()
        } else {
            viewBinding.searchView.queryHint = queryAlpha
            observeDrinksByAlphabet()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.radio1 -> {
                viewBinding.searchView.setQuery(" ", false)
                viewBinding.searchView.queryHint = query
                changeStateRadio1()
                observeDrinks()
            }
            R.id.radio2 -> {
                viewBinding.searchView.setQuery(" ", false)
                viewBinding.searchView.queryHint = queryAlpha
                changeStateRadio2()
                observeDrinksByAlphabet()
            }
        }
    }

    private fun setupViewModel() {
        viewModel()
    }

    private fun observeDrinks() {

        val drinksRv: RecyclerView = viewBinding.root.findViewById(R.id.rvRecipes)
        drinksRv.layoutManager = LinearLayoutManager(activity)

        viewModel.getDrinkList(query!!).observe(viewLifecycleOwner) {
            if (it[0].name.toString().equals(null)) {
                Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT).show()
            } else {
                drinksAdapter.setDrinks(it)
                Log.e("TAG", "observeDrinks: ${it[2].name.toString()}")
            }
        }
        drinksAdapter = DrinksAdapter(context as Activity, this)
        drinksRv.setAdapter(drinksAdapter)
    }

    private fun observeDrinksByAlphabet() {

        val drinksRv: RecyclerView = viewBinding.root.findViewById(R.id.rvRecipes)
        drinksRv.layoutManager = LinearLayoutManager(activity)

        viewModel.getDrinkListByAlphabet(queryAlpha!!).observe(viewLifecycleOwner) {
            if (it[0].name.toString().equals(null)) {
                Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT).show()
            } else {
                drinksAdapter.setDrinks(it)
                Log.e("TAG", "observeDrinks: ${it[2].name.toString()}")
            }
        }
        drinksAdapter = DrinksAdapter(context as Activity, this)
        drinksRv.setAdapter(drinksAdapter)
    }

    private fun changeStateRadio2() {
        viewBinding.radio2.isChecked = true
        viewBinding.radio1.isChecked = false
        viewBinding.radio1.setTextColor(Color.parseColor("#B1ADAD"))
        viewBinding.radio2.setTextColor(Color.parseColor("#FF000000"))
    }

    private fun changeStateRadio1() {
        viewBinding.radio1.isChecked = true
        viewBinding.radio2.isChecked = false
        viewBinding.radio1.setTextColor(Color.parseColor("#FF000000"))
        viewBinding.radio2.setTextColor(Color.parseColor("#B1ADAD"))
    }

    override fun onItemClick(drinkModel: DrinkModel, position: Int) {

        drinkModel.changeFavIcon = true

        val drinkName = drinkModel.name
        val drinkInstr = drinkModel.instruction
        val drinkPic = drinkModel.image
        if (drinkModel.alcoholic == "Alcoholic") {
            alcohol = true
        }

        lifecycleScope.launch {
            viewModel.addToFavourites(
                FavoriteModel(
                    drinkName!!,
                    drinkInstr!!,
                    alcohol,
                    drinkPic!!
                )
            )
        }
        Toast.makeText(activity, "Recipe Added to favorites", Toast.LENGTH_SHORT).show()
        drinksAdapter.notifyDataSetChanged()
    }
}
