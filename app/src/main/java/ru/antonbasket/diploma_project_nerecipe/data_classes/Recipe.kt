package ru.antonbasket.diploma_project_nerecipe.data_classes

data class Recipe(
    val id: Long,
    val name: String,
    val position: Int,
    val author: String,
    val likes: Int = 0,
    val cuisine: String,
    val content: String,
    val isLiked: Boolean = false
)
