package ru.antonbasket.diploma_project_nerecipe.view_holders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Cuisine
import ru.antonbasket.diploma_project_nerecipe.databinding.CuisineNameBinding

class CuisineViewHolder(
    private val binding: CuisineNameBinding,
    private val cuisineClickedListener: CuisineClickedListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cuisine: Cuisine) {
        cuisineBinding(cuisine, binding, cuisineClickedListener)
    }
}

fun cuisineBinding(
    cuisine: Cuisine,
    binding: CuisineNameBinding,
    cuisineClickedListener: CuisineClickedListener,
) {
    binding.apply {
        with(cuisineSelector) {
            id = cuisine.id
            tag = cuisine.rusName
            text = cuisine.rusName
            isChecked = cuisine.isSelect
            textOn = cuisine.rusName
            textOff = cuisine.rusName

            val initialColor = textColors.defaultColor
            setTextColor(initialColor)

            setOnClickListener {
                if (isChecked) {
                    textOn = cuisine.rusName
                    cuisine.isSelect = true
                    setTextColor(Color.WHITE)
                } else {
                    isChecked = false
                    textOff = cuisine.rusName
                    cuisine.isSelect = false
                    setTextColor(initialColor)
                }
                cuisineClickedListener.cuisineClicked(cuisine)
            }
        }
    }
}