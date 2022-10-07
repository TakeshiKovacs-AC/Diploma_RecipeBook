package ru.antonbasket.diploma_project_nerecipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val position: Int,
    val author: String,
    val likes: Int = 0,
    val cuisine: String,
    val content: String,
    val isLiked: Boolean = false
) {
    fun toData() = Recipe(id, name, position, author, likes, cuisine, content, isLiked)

    companion object {
        fun fromData(dto: Recipe) =
            RecipeEntity(dto.id, dto.name, dto.position, dto.author, dto.likes, dto.cuisine, dto.content,
                dto.isLiked)
    }
}
