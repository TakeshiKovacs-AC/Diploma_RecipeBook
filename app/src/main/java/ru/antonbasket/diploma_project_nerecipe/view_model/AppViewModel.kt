package ru.antonbasket.diploma_project_nerecipe.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.antonbasket.diploma_project_nerecipe.DataBase
import ru.antonbasket.diploma_project_nerecipe.EmptyContent
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.repositories.AppRepo
import ru.antonbasket.diploma_project_nerecipe.repositories.AppRepoImpl

private val recipeEmpty = EmptyContent.recipeAbsence
private val instructionsEmpty = EmptyContent.instructionAbsence

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepo = AppRepoImpl(
        DataBase.getExample(context = application).instructionsDao(),
        DataBase.getExample(context = application).recipeDao()
    )
    val data = repository.getAll()
    val recipeContent = MutableLiveData(recipeEmpty)
    val recipeData = repository.getRecipeFromList()

    val instructionsData = repository.getAllInstructions()
    private val instructionsContent = MutableLiveData(instructionsEmpty)

    fun likeById(id: Long) = repository.likeById(id)
    fun getRecById(id: Long) = repository.getRecById(id)
    fun deleteById(id: Long) = repository.delete(id)
    fun getByUsingFilter(name: String, author: String, cuisine: String, isLiked: Boolean) =
        repository.getByUsingFilter(name, author, cuisine, isLiked)
    fun getByNameInFilter(name: String) = repository.getByNameInFilter(name)
    fun getByAuthorInFilter(author: String) = repository.getByAuthorInFilter(author)
    fun getByCuisineInFilter(cuisine: String) = repository.getByCuisineInFilter(cuisine)
    fun getByLikesInFilter(isLiked: Boolean) = repository.getByLikesInFilter(isLiked)
    private fun getInstructionById(recipeId: Long) = repository.getInstructionById(recipeId)
    fun deleteInstruction(instructions: Instructions) = repository.deleteInstruction(instructions)

    fun emptyContent() {
        recipeContent.value = recipeEmpty
    }

    fun saveRecipe() {
        recipeContent.value?.let {
            repository.save(it)
        }
        emptyContent()
    }

    fun fixRecipe(recipe: Recipe) {
        recipeContent.value = recipe
    }

    fun contentAmending(position: Int, name: String, author: String, cuisine: String, content: String) {
        val nameValue = name.trim()
        val authorValue = author.trim()
        val cuisineValue = cuisine.trim()
        val contentValue = content.trim()
        if (recipeContent.value?.author == authorValue &&
            recipeContent.value?.name == nameValue &&
            recipeContent.value?.cuisine == cuisineValue &&
            recipeContent.value?.content == contentValue
        ) {
            return
        }
        recipeContent.value = recipeContent.value?.copy(
            name = nameValue,
            author = authorValue,
            position = position,
            cuisine = cuisineValue,
            content = contentValue)
    }

    fun upDateInstructions() {
        recipeContent.value.let {
            it?.id
        }?.let {
            recipeContent.value?.let {
                getInstructionById(it.id)
            }
        }
    }

    fun saveInstruction() {
        instructionsContent.value?.let {
            repository.saveInstruction(it)
        }
        upDateInstructions()
        instructionsContent.value = instructionsEmpty
    }

    fun fixInstructions(instructions: Instructions) {
        instructionsContent.value = instructions
    }

    fun amendInstructionsValue(position: Int, title: String,description: String) {
        val getName = title.trim()
        val getText = description.trim()
        if (instructionsContent.value?.title == getName &&
            instructionsContent.value?.description == getText
        ) {
            return
        }
        instructionsContent.value = recipeContent.value.let {
            it?.id
        }?.let {
            instructionsContent.value?.copy(
                title = getName,
                description = getText,
                position = position,
                recipeId = it
            )
        }
    }
}
