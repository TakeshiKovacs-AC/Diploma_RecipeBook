package ru.antonbasket.diploma_project_nerecipe.dao_room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.antonbasket.diploma_project_nerecipe.entity.RecipeEntity

@Dao
interface DaoOfRecipe {

//    @Query("SELECT * FROM RecipeEntity ORDER BY id DESC")
//    fun getInitAll(): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity ORDER BY position DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM RecipeEntity WHERE" +
            " CASE WHEN :isFiltered THEN author IN (:author)" +
            " OR name IN (:name) OR cuisine IN (:cuisine)" +
            " OR isLiked IN (:isLiked) ELSE 1 END ORDER BY position DESC")
//    @Query("SELECT * FROM RecipeEntity WHERE author = :author OR name = :name OR category = :category ORDER BY id DESC")
    fun getByUsingFilter(
        name: String,
        author: String,
        isFiltered: Boolean,
        cuisine: String,
        isLiked: Boolean
    ): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE " +
            "CASE WHEN :isFiltered THEN name LIKE :name " +
            "END ORDER BY position DESC")
    fun getByFilteredName(
        name: String,
        isFiltered: Boolean
    ): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE " +
            "CASE WHEN :isFiltered THEN author LIKE :author " +
            "END ORDER BY position DESC")
    fun getByFilteredAuthor(
        author: String,
        isFiltered: Boolean
    ): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE " +
            "CASE WHEN :isFiltered THEN cuisine LIKE :cuisine " +
            "END ORDER BY position DESC")
    fun getByFilteredCuisine(
        cuisine: String,
        isFiltered: Boolean
    ): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE " +
            "CASE WHEN :isFiltered THEN isLiked IN (:isLiked) " +
            "END ORDER BY position DESC")
    fun getByFilteredIsLike(
        isLiked: Boolean,
        isFiltered: Boolean
    ): List<RecipeEntity>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    fun getById(id: Long): RecipeEntity

    @Query("UPDATE RecipeEntity SET id = :id, position = :newPosition WHERE id = :id AND position = :oldPosition")
    fun updatePositionById(id: Long, oldPosition: Int, newPosition: Int)

    @Query("UPDATE RecipeEntity SET author = :author, name = :name, cuisine = :cuisine, content = :content WHERE id = :id")
    fun updateById(id: Long, name: String, author: String, cuisine: String, content: String)

    fun saveRecipe(recipe: RecipeEntity) =
        if (recipe.id == 0L) insert(recipe) else {
            updateById(recipe.id, recipe.name, recipe.author,  recipe.cuisine, recipe.content)
        }

    @Query(
        """
        UPDATE RecipeEntity SET
        likes = likes + CASE WHEN isLiked THEN -1 ELSE 1 END,
        isLiked = CASE WHEN isLiked THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun makeLikeById(id: Long)

    @Query("DELETE FROM RecipeEntity WHERE id = :id")
    fun deleteById(id: Long)
}
