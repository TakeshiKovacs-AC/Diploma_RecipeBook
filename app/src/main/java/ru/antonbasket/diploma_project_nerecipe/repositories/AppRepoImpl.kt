package ru.antonbasket.diploma_project_nerecipe.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.antonbasket.diploma_project_nerecipe.EmptyContent
import ru.antonbasket.diploma_project_nerecipe.dao_room.DaoOfInstructions
import ru.antonbasket.diploma_project_nerecipe.dao_room.DaoOfRecipe
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.entity.InstructionsEntity
import ru.antonbasket.diploma_project_nerecipe.entity.RecipeEntity

class AppRepoImpl(
    private val roomInstructions: DaoOfInstructions,
    private val roomRecipe: DaoOfRecipe
) : AppRepo {

    private val data: MutableLiveData<List<Recipe>> = Transformations.map(roomRecipe.getAll()) { list ->
        list.map {
            it.toData()
        }
    } as MutableLiveData<List<Recipe>>

    private val recipeData = MutableLiveData(EmptyContent.recipeAbsence)
    private var instructionsData: MutableLiveData<List<Instructions>> = MutableLiveData(emptyList())

    override fun getAll() = data
    override fun getAllInstructions() = instructionsData

    override fun getByUsingFilter(
        name: String,
        author: String,
        cuisine: String,
        isLiked: Boolean
    ) {
        val ifFiltered = author != "" || name != "" || cuisine != "" || isLiked
        data.value =
            roomRecipe.getByUsingFilter(author, name, ifFiltered, cuisine, isLiked).map { it.toData() }
    }

    override fun getByNameInFilter(
        name: String,
    ) {
        val isUsedFilter = name != ""
        data.value =
            roomRecipe.getByFilteredName(name, isUsedFilter).map { it.toData() }
    }

    override fun getByAuthorInFilter(
        author: String,
    ) {
        val isUsedFilter = author != ""
        data.value =
            roomRecipe.getByFilteredAuthor(author, isUsedFilter).map { it.toData() }
    }

    override fun getByCuisineInFilter(
        cuisine: String,
    ) {
        val isUsedFilter = cuisine != ""
        data.value =
            roomRecipe.getByFilteredCuisine(cuisine, isUsedFilter).map { it.toData() }
    }

    override fun getByLikesInFilter(
        isLiked: Boolean,
    ) {
        data.value =
            roomRecipe.getByFilteredIsLike(isLiked, isLiked).map { it.toData() }
    }

    override fun getRecById(id: Long): Recipe? {
        recipeData.value = roomRecipe.getById(id).toData()
        getInstructionById(id)
        return recipeData.value
    }

    override fun getRecipeFromList(): LiveData<Recipe> = recipeData

    override fun save(initialRecipe: Recipe) {
        roomRecipe.saveRecipe(RecipeEntity.fromData(initialRecipe))
    }

    override fun delete(id: Long) {
        roomRecipe.deleteById(id)
        roomInstructions.deleteByRecipeId(id)

        val recipesEntity = roomRecipe.getAll().value
        if (recipesEntity != null) {
            for ((index) in recipesEntity.withIndex()) {
                val oldPosition = recipesEntity[index].position
                roomRecipe.updatePositionById(id, oldPosition, index + 1)
            }
        }
    }

    override fun getInstructionById(recipeId: Long) {
        instructionsData.value = roomInstructions.getStepById(recipeId).map {
            it.toData()
        }
    }

    override fun likeById(id: Long) {
        roomRecipe.makeLikeById(id)
        getRecById(id)
    }

    override fun saveInstruction(instructions: Instructions) {
        roomInstructions.save(InstructionsEntity.fromData(instructions))
        getInstructionById(instructions.recipeId)
    }

    override fun deleteInstruction(instructions: Instructions) {
        roomInstructions.deleteById(instructions.id)

        val instructionsEntity = roomInstructions.getStepById(instructions.recipeId)
        for ((index) in instructionsEntity.withIndex()) {
            val oldPosition = instructionsEntity[index].position
            roomInstructions.updatePositionById(instructions.recipeId, oldPosition, index + 1)
        }
        getInstructionById(instructions.recipeId)
    }
}

//        Transformations.map(daoStage.getAll()) { list ->
//            list.map {
//                it.toDto()
//            }
//        } as MutableLiveData<List<Stage>>

//    init {
//        // для первоначальной записи первых постов
//        //for(recipe in recipes) { dao.save(RecipeEntity.fromDto(recipe)) }
//
//        data.value = dao.getInitAll().let { list ->
//            list.map {
//                it.toDto()
//                //recipes = listOf(it.toDto()) + recipes
//            }
//        }
//        //data.value = recipes
//    }