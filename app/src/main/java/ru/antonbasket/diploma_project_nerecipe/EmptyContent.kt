package ru.antonbasket.diploma_project_nerecipe

import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe

object EmptyContent {
    val recipeAbsence = Recipe(
        id = 0,
        name = "No name",
        position = 0,
        author = "No author",
        likes = 0,
        cuisine = "Not chosen",
        content = "No content",
        isLiked = false
    )

    val instructionAbsence = Instructions(
        id = 0,
        position = 0,
        title = "No title",
        recipeId = 0,
        description = "No description"
    )
}