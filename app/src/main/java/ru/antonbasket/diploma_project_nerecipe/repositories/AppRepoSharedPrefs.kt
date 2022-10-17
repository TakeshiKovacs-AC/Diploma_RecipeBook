package ru.antonbasket.diploma_project_nerecipe.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.antonbasket.diploma_project_nerecipe.EmptyContent
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe

const val UNREAL_ID = 1_000_000_000L

class AppRepoSharedPrefs(
    context: Context,
) : AppRepo {
    private var followId = 1L
    private val gson = Gson()
    private var recipesList = emptyList<Recipe>()
    private val data = MutableLiveData(recipesList)
    private val postData = MutableLiveData(EmptyContent.recipeAbsence)
    private val type = TypeToken.getParameterized(List::class.java, Recipe::class.java).type
    private val key = "postsList"
    private val sharedPrefs = context.getSharedPreferences("repository", Context.MODE_PRIVATE)

    init {
        sharedPrefs.getString(key, null)?.let {
            recipesList = gson.fromJson(it, type)
            data.value = recipesList
        }
    }

    private fun update() {
        with(sharedPrefs.edit()) {
            putString(key, gson.toJson(recipesList))
            apply()
        }
    }

    override fun getAll(): LiveData<List<Recipe>> = data
    override fun getAllInstructions(): LiveData<List<Instructions>> {
        return MutableLiveData(emptyList())
    }
    override fun getByUsingFilter(name: String, author: String, cuisine: String, isLiked: Boolean) {}
    override fun getByNameInFilter(name: String) {}
    override fun getByAuthorInFilter(author: String) {}
    override fun getByCuisineInFilter(cuisine: String) {}
    override fun getByLikesInFilter(isLiked: Boolean) {}
    override fun getInstructionById(recipeId: Long) {}
    override fun saveInstruction(instructions: Instructions) {}
    override fun deleteInstruction(instructions: Instructions) {}
    override fun getRecipeFromList(): LiveData<Recipe> = postData
    override fun getRecById(id: Long): Recipe? {
        postData.value = recipesList.find {
            it.id == id
        }
        return postData.value
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == UNREAL_ID) {
            recipesList = listOf(
                recipe.copy(
                    id = recipe.id,
                    author = "Person",
                    name = "Some dish",
                    content = recipe.content,
                    isLiked = false
                )
            ) + recipesList
            data.value = recipesList
            update()
            return
        }

        if (recipe.id == 0L) {
            recipesList = listOf(
                recipe.copy(
                    id = followId++,
                    author = "Person",
                    name = "Some dish",
                    isLiked = false
                )
            ) + recipesList
            data.value = recipesList
            update()
            return
        }

        recipesList = recipesList.map {
            if (it.id != recipe.id)
                it
            else
                it.copy(content = recipe.content)
        }
        data.value = recipesList
        update()
    }

    override fun likeById(id: Long) {
        recipesList = recipesList.map {
            if (it.id != id)
                it
            else
                it.copy(
                    isLiked = !it.isLiked,
                    likes = if (it.isLiked) it.likes - 1 else it.likes + 1
                )
        }
        data.value = recipesList
        update()
    }

    override fun delete(id: Long) {
        recipesList = recipesList.filter { it.id != id }
        data.value = recipesList
        update()
    }
}

fun getUnrealPostId(): Long = UNREAL_ID