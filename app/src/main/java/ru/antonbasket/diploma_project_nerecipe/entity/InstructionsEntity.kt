package ru.antonbasket.diploma_project_nerecipe.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions

@Entity
data class InstructionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var position: Int,
    val recipeId: Long,
    val title: String,
    val description: String
) {
    fun toData() = Instructions(id, position, recipeId, title, description)

    companion object {
        fun fromData(dto: Instructions) =
            InstructionsEntity(dto.id, dto.position, dto.recipeId, dto.title, dto.description)
    }
}
