package ru.antonbasket.diploma_project_nerecipe.adapters

import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe

interface RecipeClickedListener {
    fun nameClicked(recipe: Recipe)
    fun authorClicked(recipe: Recipe)
    fun editClicked(recipe: Recipe)
    fun removeClicked(recipe: Recipe)
    fun likeClicked(recipe: Recipe)
    fun cuisineClicked(recipe: Recipe)
    fun contentClicked(recipe: Recipe)
}