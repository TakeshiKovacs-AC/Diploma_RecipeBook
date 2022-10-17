package ru.antonbasket.diploma_project_nerecipe.repositories

import androidx.lifecycle.LiveData
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe

interface AppRepo {
    fun getAllInstructions(): LiveData<List<Instructions>>
    fun getInstructionById(recipeId: Long)
    fun saveInstruction(instructions: Instructions)
    fun deleteInstruction(instructions: Instructions)

    fun getAll(): LiveData<List<Recipe>>
    fun getRecById(id: Long): Recipe?
    fun likeById(id: Long)
    fun getRecipeFromList(): LiveData<Recipe>
    fun save(recipe: Recipe)
    fun delete(id: Long)
    fun getByNameInFilter(name: String)
    fun getByAuthorInFilter(author: String)
    fun getByCuisineInFilter(cuisine: String)
    fun getByLikesInFilter(isLiked: Boolean)
    fun getByUsingFilter(name: String, author: String, cuisine: String, isLiked: Boolean)

}