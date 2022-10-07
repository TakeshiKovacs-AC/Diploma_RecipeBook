package ru.antonbasket.diploma_project_nerecipe.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.repositories.AppRepo
import ru.antonbasket.diploma_project_nerecipe.repositories.AppRepoSharedPrefs
import ru.antonbasket.diploma_project_nerecipe.repositories.getUnrealPostId

class ExistingRecipeContent(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepo = AppRepoSharedPrefs(application)

    fun saveContent(text: String) {
        val testRecipe = Recipe(
            id = getUnrealPostId(),
            name = "EMPTY",
            author = "EMPTY",
            cuisine = "EMPTY",
            position = 0,
            content = text,
            isLiked = false
        )
        repository.save(testRecipe)
    }

    fun getRecById(id: Long) = repository.getRecById(id)
}