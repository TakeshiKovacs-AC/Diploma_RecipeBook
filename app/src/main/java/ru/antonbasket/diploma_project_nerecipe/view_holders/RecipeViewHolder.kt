package ru.antonbasket.diploma_project_nerecipe.view_holders

import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.adapters.RecipeClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentRecipeViewBinding

class RecipeViewHolder(
    private val binding: FragmentRecipeViewBinding,
    private val recipeClickedListener: RecipeClickedListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: Recipe) {
        recipeBinding(recipe, binding, recipeClickedListener)
    }
}

fun recipeBinding(
    recipe: Recipe,
    binding: FragmentRecipeViewBinding,
    recipeClickedListener: RecipeClickedListener
) {
    binding.apply {
        recipeWindow.tag = recipe.position
        author.text = recipe.author
        favoriteRecipe.isChecked = recipe.isLiked
        cuisine.text = recipe.cuisine
        recipeName.text = recipe.name
        favoriteRecipe.text = recipe.likes.toString()

//        stageContent.adapter = StagesAdapter(onInteractionStageListener = object : OnInteractionStageListener{
//            override fun onClicked(stage: Stage) {
        // TO DO("Not yet implemented")
        //onInteractionListener.onStage(stage)
//            }
//        })
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
//                        R.id.add -> {
//                            onInteractionListener.onAdd()
//                            true
//                        }
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