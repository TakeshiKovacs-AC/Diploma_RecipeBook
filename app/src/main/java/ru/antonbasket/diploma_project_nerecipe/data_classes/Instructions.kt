package ru.antonbasket.diploma_project_nerecipe.data_classes

data class Instructions(
    val id: Int,
    var position: Int,
    val recipeId: Long,
    val title: String,
    val description: String
)
