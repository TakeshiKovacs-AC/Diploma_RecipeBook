package ru.antonbasket.diploma_project_nerecipe.dao_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.antonbasket.diploma_project_nerecipe.entity.InstructionsEntity

@Dao
interface DaoOfInstructions {

//    @Query("SELECT * FROM StageEntity ORDER BY pos DESC")
//    fun getAll(): LiveData<List<StageEntity>>

//    @Query("SELECT * FROM StageEntity WHERE id = :id")
//    fun getById(id: Int): StageEntity

    @Query("SELECT * FROM InstructionsEntity WHERE recipeId IN (:recipeId) ORDER BY position ASC")
    fun getStepById(recipeId: Long): List<InstructionsEntity>

    @Insert
    fun insert(stage: InstructionsEntity)

    @Query("UPDATE InstructionsEntity SET recipeId = :recipeId, position = :position, title = :title, description = :description WHERE id = :id")
    fun updateById(id: Int, recipeId: Long, position: Int, title: String, description: String)

    @Query("UPDATE InstructionsEntity SET recipeId = :recipeId, position = :newPosition WHERE recipeId = :recipeId AND position = :oldPosition")
    fun updatePositionById(recipeId: Long, oldPosition: Int, newPosition: Int)

    fun save(instruction: InstructionsEntity) =
        if (instruction.id == 0) insert(instruction) else {
            updateById(instruction.id, instruction.recipeId, instruction.position, instruction.title, instruction.description)
        }

    @Query("DELETE FROM InstructionsEntity WHERE recipeId = :recipeId")
    fun deleteByRecipeId(recipeId: Long)

    @Query("DELETE FROM InstructionsEntity WHERE id = :id")
    fun deleteById(id: Int)


}