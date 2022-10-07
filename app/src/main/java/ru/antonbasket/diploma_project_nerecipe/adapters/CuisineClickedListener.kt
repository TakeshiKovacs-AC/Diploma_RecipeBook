package ru.antonbasket.diploma_project_nerecipe.adapters

import ru.antonbasket.diploma_project_nerecipe.data_classes.Cuisine

interface CuisineClickedListener {
    fun cuisineClicked(cuisine: Cuisine)
}