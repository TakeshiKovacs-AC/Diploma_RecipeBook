package ru.antonbasket.diploma_project_nerecipe.data_classes

data class Cuisine(
    val id: Int,
    val engName: String,
    val rusName: String,
    var isSelect: Boolean = false
)
