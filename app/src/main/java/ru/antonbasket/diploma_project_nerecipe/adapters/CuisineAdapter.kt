package ru.antonbasket.diploma_project_nerecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.data_classes.Cuisine
import ru.antonbasket.diploma_project_nerecipe.databinding.CuisineNameBinding
import ru.antonbasket.diploma_project_nerecipe.view_holders.CuisineViewHolder

class CuisineAdapter (
    private val cuisineClickedListener: CuisineClickedListener
    ) : RecyclerView.Adapter<CuisineViewHolder>() {

        val cuisinesList = listOf(
            Cuisine(1, "European", "Европейская"),
            Cuisine(2, "Russian", "Русская"),
            Cuisine(3, "Oriental", "Азиатская"),
            Cuisine(4, "Mediterranean", "Средиземноморская"),
            Cuisine(5, "Panasian", "Паназиатская"),
            Cuisine(6, "Eastern", "Восточная"),
            Cuisine(7, "American", "Американская"),
        )
        override fun getItemCount(): Int = cuisinesList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CuisineNameBinding.inflate(inflater, parent, false)
            return CuisineViewHolder(binding, cuisineClickedListener)
        }

        override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
            val category = cuisinesList[position]
            holder.bind(category)
        }
    }



