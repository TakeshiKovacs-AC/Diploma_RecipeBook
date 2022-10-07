package ru.antonbasket.diploma_project_nerecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.databinding.InstructionsRangeBinding
import ru.antonbasket.diploma_project_nerecipe.view_holders.InstructionsViewHolder
import ru.antonbasket.diploma_project_nerecipe.view_holders.StageDiffCallback

class InstructionsAdapter(
    private val instructionsClickedListener: InstructionsClickedListener,
) : ListAdapter<Instructions, InstructionsViewHolder>(StageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InstructionsRangeBinding.inflate(inflater, parent, false)
        return InstructionsViewHolder(binding, instructionsClickedListener)
    }

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val instruction = getItem(position)
        holder.bind(instruction)
    }
}

//    private val pictureId = R.drawable.goodfood

//    val recipeId = 1L
//    val listStage = listOf(
//        Instructions(0, recipeId, 1, "Приготовление ингридиентов", "Берем тото...", pictureId),
//        Instructions(1, recipeId, 2, "Нарезка", "Нарезаем так-то", pictureId),
//        Instructions(2, recipeId, 3, "Варка", "Варим столько-то времени", pictureId),
//        Instructions(3, recipeId, 4, "Промывка", "Под холодной водой промываем и охлаждаем", pictureId),
//        Instructions(4, recipeId, 5, "Детальная нарезка", "Мелко нарезаем", pictureId),
//        Instructions(5, recipeId, 6, "Приготовление соуса", "Смешиваем для приготовления...", pictureId),
//        Instructions(6, recipeId, 7, "Укладываем", "Расскладываем по краям тарелки...", pictureId),
//        Instructions(7,
//            recipeId,
//            8,
//            "Заправка соусом",
//            "Заправляем ингридиенты соусом следующим образом...",
//            pictureId),
//        Stage(7, idRecipe, 9, "Оформление и подача", "Подаем в охлажденном виде", pictureId)
//    )