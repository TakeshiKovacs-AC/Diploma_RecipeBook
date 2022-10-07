package ru.antonbasket.diploma_project_nerecipe.adapters

import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions

interface InstructionsClickedListener {
    fun instructionsClicked(instructions: Instructions)
}