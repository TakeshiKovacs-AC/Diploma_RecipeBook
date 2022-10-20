package ru.antonbasket.diploma_project_nerecipe.view_holders

import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.RecipeClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentRecipeViewBinding

class RecipeViewHolder(
    private val cuisineAdapter: CuisineAdapter,
    private val binding: FragmentRecipeViewBinding,
    private val recipeClickedListener: RecipeClickedListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(recipe: Recipe) {
        recipeBinding(recipe, binding, recipeClickedListener, cuisineAdapter)
    }
}

fun recipeBinding(
    recipe: Recipe,
    binding: FragmentRecipeViewBinding,
    recipeClickedListener: RecipeClickedListener,
    cuisineAdapter: CuisineAdapter
) {
    binding.apply {

        recipeWindow.tag = recipe.position
        author.text = recipe.author
        favoriteRecipe.isChecked = recipe.isLiked
        cuisine.text = recipe.cuisine
        recipeName.text = recipe.name
        favoriteRecipe.text = recipe.likes.toString()

        when(recipe.cuisine) {
            cuisineAdapter.cuisinesList[0].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.european)
            cuisineAdapter.cuisinesList[1].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.russian)
            cuisineAdapter.cuisinesList[2].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.oriental)
            cuisineAdapter.cuisinesList[3].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.mediterranean)
            cuisineAdapter.cuisinesList[4].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.panasian)
            cuisineAdapter.cuisinesList[5].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.eastern)
            cuisineAdapter.cuisinesList[6].rusName.trim() ->
                binding.mainRecipe.setBackgroundResource(R.drawable.american)
            }

        author.setOnClickListener {
            recipeClickedListener.authorClicked(recipe)
        }
        recipeName.setOnClickListener {
            recipeClickedListener.nameClicked(recipe)
        }
        cuisine.setOnClickListener {
            recipeClickedListener.cuisineClicked(recipe)
        }
        favoriteRecipe.setOnClickListener {
            recipeClickedListener.likeClicked(recipe)
        }

        menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.recipe_actions_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            recipeClickedListener.removeClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            recipeClickedListener.editClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}