package ru.antonbasket.diploma_project_nerecipe

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AppRecipeListUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}