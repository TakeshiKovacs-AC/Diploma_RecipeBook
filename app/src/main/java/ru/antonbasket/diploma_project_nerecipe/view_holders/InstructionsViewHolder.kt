package ru.antonbasket.diploma_project_nerecipe.view_holders


import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.adapters.InstructionsClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.databinding.InstructionsRangeBinding

class InstructionsViewHolder(
    private val binding: InstructionsRangeBinding,
    private val instructionsClickedListener: InstructionsClickedListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(instructions: Instructions) {
        instructionBinding(instructions, binding, instructionsClickedListener)
    }
}
fun instructionBinding(
    instructions: Instructions,
    binding: InstructionsRangeBinding,
    instructionsClickedListener: InstructionsClickedListener,
) {
    binding.apply {
        with(instructionDescription) {
            text =
                "Шаг ${instructions.position}\n${instructions.title}\n${instructions.description}"

            setOnClickListener {
                instructionsClickedListener.instructionsClicked(instructions)
            }
        }

        with(instructionCard) {
            tag = instructions.position
        }
    }
}

class StageDiffCallback : DiffUtil.ItemCallback<Instructions>() {
    override fun areItemsTheSame(oldItem: Instructions, newItem: Instructions): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Instructions, newItem: Instructions): Boolean {
        return oldItem == newItem
    }
}