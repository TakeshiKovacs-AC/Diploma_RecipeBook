package ru.antonbasket.diploma_project_nerecipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.AppRecipeListUtils
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.StringArguments
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineClickedListener
import ru.antonbasket.diploma_project_nerecipe.adapters.InstructionsAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.InstructionsClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Cuisine
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentNewRecipeBinding
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrDescrArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrPlaceArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrTitleArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.newInstrArguments
import ru.antonbasket.diploma_project_nerecipe.view_model.AppViewModel

class NewRecipeFragment: Fragment() {
    private lateinit var instructionsInstance: Instructions
    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewRecipeBinding.inflate(
            inflater,
            container,
            false
        )
        val testContentValue: String? = null
        val recipeId = arguments?.cuisineIdArguments?.toLong() ?: 0
        val cuisineAdapter = CuisineAdapter(object : CuisineClickedListener {
            override fun cuisineClicked(cuisine: Cuisine) {
                binding.cuisineList.text = cuisine.rusName.trim()
            }
        })
        binding.cuisineSelector.adapter = cuisineAdapter

        val instructionsAdapter = InstructionsAdapter(object : InstructionsClickedListener {
            override fun instructionsClicked(instructions: Instructions) {
                instructionsInstance = instructions
                binding.edit.text = "?????? ${instructions.position}\n${instructions.title}\n${instructions.description}"
            }
        })
        binding.listOfInstructions.adapter = instructionsAdapter
        viewModel.instructionsData.observe(viewLifecycleOwner) {
            if (recipeId != 0L && it.isNotEmpty()) {
                instructionsAdapter.submitList(it)
                binding.root.findViewById<TextView>(R.id.noAnyInstructions).visibility =
                    View.INVISIBLE
                binding.root.findViewById<RecyclerView>(R.id.listOfInstructions).visibility =
                    View.VISIBLE
            } else {
                binding.root.findViewById<TextView>(R.id.noAnyInstructions).visibility =
                    View.VISIBLE
                binding.root.findViewById<RecyclerView>(R.id.listOfInstructions).visibility =
                    View.INVISIBLE
            }
        }

        binding.okButton.setOnClickListener {
            val cuisineChecking = binding.cuisineList.text.trim().toString()
            if (
                binding.authorInput.text.isNullOrBlank() ||
                binding.reciteTitleInput.text.isNullOrBlank() ||
                cuisineChecking == R.string.cuisineSelection.toString()
            ) {
                Toast.makeText(activity, R.string.fieldsNotFilled, Toast.LENGTH_SHORT).
                show()
                return@setOnClickListener
            }

            if (!binding.edit.text.isNullOrBlank()) {
                if (testContentValue != null && viewModel.recipeContent.value?.id != 0L) {
                    viewModel.emptyContent()
                }
                viewModel.contentAmending(
                    arguments?.positionIdArguments.let { it?.toInt() } ?: 0,
                    binding.authorInput.text.toString(),
                    binding.reciteTitleInput.text.toString(),
                    binding.cuisineList.text.toString(),
                    binding.edit.text.toString()
                )
                viewModel.saveRecipe()
            }
            AppRecipeListUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        arguments?.recipeAuthorArguments?.let(binding.authorInput::setText)
        arguments?.recipeNameArguments?.let(binding.reciteTitleInput::setText)
        arguments?.cuisineArguments?.let(binding.cuisineList::setText)
            ?: binding.cuisineList.setText(R.string.cuisineSelection)
        arguments?.instrDescrArguments?.let(binding.edit::setText)
            ?: binding.edit.setText(R.string.editStageSelection)

        binding.menuButton.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.recipe_sequence_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add -> {
                            if (recipeId == 0L) {
                                Toast.makeText(activity, R.string.noParentObj, Toast.LENGTH_SHORT).
                                show()
                            } else {
                                findNavController().navigate(
                                    R.id.newRecipeFragment_to_newInstructionsFragment,
                                    Bundle().apply {
                                        newInstrArguments = recipeId.toString()
                                        val instructionsCounting = viewModel.instructionsData.value
                                        instrPlaceArguments =
                                            if (instructionsCounting != null)
                                                (instructionsCounting.count() + 1).toString()
                                            else 1.toString()
                                        instrDescrArguments = ""
                                        instrTitleArguments = ""
                                    }
                                )
                            }
                            true
                        }
                        R.id.edit -> {
                            if (::instructionsInstance.isInitialized) {
                                viewModel.fixInstructions(instructionsInstance)
                                findNavController().navigate(
                                    R.id.newRecipeFragment_to_newInstructionsFragment,
                                    Bundle().apply {
                                        newInstrArguments = recipeId.toString()
                                        instrPlaceArguments =
                                            instructionsInstance.position.toString()
                                        instrTitleArguments = instructionsInstance.title
                                        instrDescrArguments = instructionsInstance.description
                                    }
                                )
                            }
                            true
                        }
                        R.id.remove -> {
                            if (::instructionsInstance.isInitialized) {
                                if (recipeId == 0L) {
                                    Toast.makeText(activity, R.string.noParentObj, Toast.LENGTH_SHORT).
                                    show()
                                } else {
                                    viewModel.deleteInstruction(instructionsInstance)
                                    viewModel.upDateInstructions()
                                    binding.edit.setText(R.string.editStageSelection)
                                }
                            }
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        return binding.root
    }

    companion object {
        var Bundle.recipeNameArguments: String? by StringArguments
        var Bundle.recipeAuthorArguments: String? by StringArguments
        var Bundle.positionIdArguments: String? by StringArguments
        var Bundle.cuisineArguments: String? by StringArguments
        var Bundle.cuisineIdArguments: String? by StringArguments
        var Bundle.contentTextArguments: String? by StringArguments
    }
}
