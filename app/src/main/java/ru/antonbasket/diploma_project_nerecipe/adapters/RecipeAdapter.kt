package ru.antonbasket.diploma_project_nerecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentRecipeViewBinding
import ru.antonbasket.diploma_project_nerecipe.view_holders.RecipeDiffCallback
import ru.antonbasket.diploma_project_nerecipe.view_holders.RecipeViewHolder

class RecipeAdapter (
    private val recipeClickedListener: RecipeClickedListener
) : ListAdapter<Recipe, RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRecipeViewBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding, recipeClickedListener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}