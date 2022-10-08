package ru.antonbasket.diploma_project_nerecipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.StringArguments
import ru.antonbasket.diploma_project_nerecipe.adapters.InstructionsAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.InstructionsClickedListener
import ru.antonbasket.diploma_project_nerecipe.adapters.RecipeClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Instructions
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentRecipeViewBinding
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrDescrArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrPlaceArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.instrTitleArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewInstructionsFragment.Companion.newInstrArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.contentTextArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.cuisineArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.cuisineIdArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeAuthorArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeNameArguments
import ru.antonbasket.diploma_project_nerecipe.view_holders.recipeBinding
import ru.antonbasket.diploma_project_nerecipe.view_model.AppViewModel

class RecipeViewFragment: Fragment() {

    private val localViewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private lateinit var localInstructions: Instructions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeViewBinding.inflate(
            inflater,
            container,
            false
        )
        binding.recipeWindow.updateLayoutParams { height = -1 }

        arguments?.argumentsId?.let {
            localViewModel.getRecById(it.toLong())
        }?.let {

            val recipeClickedListener = object : RecipeClickedListener {
                private fun newFragmentForwarding(recipe: Recipe) {
                    findNavController().navigate(
                        R.id.recipeViewFragment_to_newRecipeFragment,
                        Bundle().apply {
                            cuisineIdArguments = recipe.id.toString()
                            recipeNameArguments = recipe.name
                            recipeAuthorArguments = recipe.author
                            contentTextArguments = recipe.content
                            cuisineArguments = recipe.cuisine
                        }
                    )
                }
                override fun nameClicked(recipe: Recipe) {
                    localViewModel.fixRecipe(recipe)
                    localViewModel.upDateInstructions()
                    newFragmentForwarding(recipe)
                }

                override fun authorClicked(recipe: Recipe) {
                    localViewModel.fixRecipe(recipe)
                    localViewModel.upDateInstructions()
                    newFragmentForwarding(recipe)
                }

                override fun editClicked(recipe: Recipe) {
                    localViewModel.fixRecipe(recipe)
                    localViewModel.upDateInstructions()
                    newFragmentForwarding(recipe)
                }

                override fun likeClicked(recipe: Recipe) {
                    localViewModel.likeById(recipe.id)
                }

                override fun cuisineClicked(recipe: Recipe) {
                    localViewModel.fixRecipe(recipe)
                    localViewModel.upDateInstructions()
                    newFragmentForwarding(recipe)
                }

                override fun contentClicked(recipe: Recipe) {
                    localViewModel.fixRecipe(recipe)
                    newFragmentForwarding(recipe)
                }

                override fun removeClicked(recipe: Recipe) {
                    localViewModel.deleteById(recipe.id)
                    findNavController().navigateUp()
                }
            }

            localViewModel.recipeData.observe(viewLifecycleOwner) {
                recipeBinding(it, binding, recipeClickedListener)
            }

            val instructionsAdapter = InstructionsAdapter(object : InstructionsClickedListener {
                override fun instructionsClicked(instructions: Instructions) {
                    localInstructions = instructions

                    if (::localInstructions.isInitialized) {
                        localViewModel.fixInstructions(localInstructions)
                        findNavController().navigate(
                            R.id.recipeViewFragment_to_newInstructionsFragment,
                            Bundle().apply {
                                newInstrArguments = instructions.recipeId.toString()
                                instrPlaceArguments = instructions.position.toString()
                                instrTitleArguments = instructions.title
                                instrDescrArguments = instructions.description
                            }
                        )
                    }
                }
            })
        }
        return binding.root
    }
    companion object {
        var Bundle.argumentsId: String? by StringArguments
    }
}
