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
