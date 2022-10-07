package ru.antonbasket.diploma_project_nerecipe.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.antonbasket.diploma_project_nerecipe.AppRecipeListUtils
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.CuisineClickedListener
import ru.antonbasket.diploma_project_nerecipe.adapters.RecipeAdapter
import ru.antonbasket.diploma_project_nerecipe.adapters.RecipeClickedListener
import ru.antonbasket.diploma_project_nerecipe.data_classes.Cuisine
import ru.antonbasket.diploma_project_nerecipe.data_classes.Recipe
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentMainAppBinding
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.contentTextArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.cuisineArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.cuisineIdArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.positionIdArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeAuthorArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeNameArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.RecipeViewFragment.Companion.argumentsId
import ru.antonbasket.diploma_project_nerecipe.view_model.AppViewModel

class MainAppFragment: Fragment() {

    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainAppBinding.inflate(
            inflater,
            container,
            false
        )
        val cuisineAdapter = CuisineAdapter(object : CuisineClickedListener {
            override fun cuisineClicked(cuisine: Cuisine) {
                if (cuisine.isSelect && cuisine.rusName != "Все доступные кухни")
                    viewModel.getByCuisineInFilter(cuisine.rusName.trim())
                else
                    viewModel.getByUsingFilter("", "", "", false)
            }
        })
        binding.cuisineOfRecipe.adapter = cuisineAdapter

        binding.addNewRecipe.setOnClickListener {
            findNavController().navigate(
                R.id.mainAppFragment_to_newRecipeFragment,
                Bundle().apply {
                    val valueCalculations = viewModel.data.value
                    positionIdArguments =
                        if (!valueCalculations.isNullOrEmpty())
                            (valueCalculations.maxOf { it.position } + 1).toString()
                        else 1.toString()

                    cuisineIdArguments = null
                    recipeAuthorArguments = null
                    recipeNameArguments = null
                    cuisineArguments = null
                    contentTextArguments = null
                }
            )
        }
        binding.apply {
            with(binding) {
                searching.setOnClickListener {
                    if (searchingRecipe.text.isNullOrEmpty()) {
                        viewModel.getByNameInFilter("%")
                    } else {
                        viewModel.getByNameInFilter(searchingRecipe.text.toString().trim())
                    }
                    AppRecipeListUtils.hideKeyboard(requireView())
                    allRecipes.isChecked = false
                    myRecipes.isChecked = false
                    favoriteRecipes.isChecked = false
                }
                clearLine.setOnClickListener {
                    AppRecipeListUtils.hideKeyboard(requireView())
                    searchingRecipe.setText("")
                    viewModel.getByUsingFilter("", "", "", false)
                }
            }

            with(allRecipes) {
                val instance = intArrayOf(android.R.attr.state_enabled)
                val initialColor = textColors.defaultColor

                setOnClickListener {
                    isChecked = true
                    iconTint =
                        ColorStateList(arrayOf(instance), intArrayOf(Color.YELLOW))
                    setTextColor(Color.RED)

                    viewModel.getByUsingFilter("", "", "", false)

                    myRecipes.isChecked = false
                    myRecipes.iconTint =
                        ColorStateList(arrayOf(instance), intArrayOf(Color.DKGRAY))
                    myRecipes.setTextColor(initialColor)

                    favoriteRecipes.isChecked = false
                    favoriteRecipes.iconTint =
                        ColorStateList(arrayOf(instance), intArrayOf(Color.DKGRAY))
                    favoriteRecipes.setTextColor(initialColor)
                }
            }
            with(favoriteRecipes) {
                val instance = intArrayOf(android.R.attr.state_enabled)
                val initialColor = textColors.defaultColor

                setOnClickListener {
                    isChecked = true
                    iconTint = ColorStateList(arrayOf(instance), intArrayOf(Color.BLACK))
                    setTextColor(Color.BLACK)

                    viewModel.getByUsingFilter("", "", "", false)
                    viewModel.getByLikesInFilter(true)

                    allRecipes.isChecked = false
                    allRecipes.iconTint = ColorStateList(arrayOf(instance), intArrayOf(Color.BLUE))
                    allRecipes.setTextColor(initialColor)

                    myRecipes.isChecked = false
                    myRecipes.iconTint =
                        ColorStateList(arrayOf(instance), intArrayOf(Color.DKGRAY))
                    myRecipes.setTextColor(initialColor)
                }
            }

            with(myRecipes) {
                val instance = intArrayOf(android.R.attr.state_enabled)
                val initialColor = textColors.defaultColor

                setOnClickListener {
                    isChecked = true
                    iconTint = ColorStateList(arrayOf(instance), intArrayOf(Color.YELLOW))
                    setTextColor(Color.RED)

                    viewModel.getByUsingFilter("", "", "", false)
                    viewModel.getByAuthorInFilter("Виктор Баринов")

                    allRecipes.isChecked = false
                    allRecipes.iconTint = ColorStateList(arrayOf(instance), intArrayOf(Color.GREEN))
                    allRecipes.setTextColor(initialColor)

                    favoriteRecipes.isChecked = false
                    favoriteRecipes.iconTint =
                        ColorStateList(arrayOf(instance), intArrayOf(Color.GREEN))
                    favoriteRecipes.setTextColor(initialColor)
                }
            }
        }

        val appAdapter = RecipeAdapter(object : RecipeClickedListener {
            private fun newFragmentForwarding(recipe: Recipe) {
                findNavController().navigate(
                    R.id.mainAppFragment_to_newRecipeFragment,
                    Bundle().apply {
                        recipeNameArguments = recipe.name
                        recipeAuthorArguments = recipe.author
                        cuisineIdArguments = recipe.id.toString()
                        cuisineArguments = recipe.cuisine
                        contentTextArguments = recipe.content
                    }
                )
            }

            private fun recipeNewFragForwarding(recipe: Recipe) {
                findNavController().navigate(
                    R.id.mainAppFragment_to_recipeViewFragment,
                    Bundle().apply {
                        argumentsId = recipe.id.toString()
                    }
                )
            }

            override fun nameClicked(recipe: Recipe) {
                recipeNewFragForwarding(recipe)
            }

            override fun authorClicked(recipe: Recipe) {
                recipeNewFragForwarding(recipe)
            }

            override fun contentClicked(recipe: Recipe) {
                recipeNewFragForwarding(recipe)
            }

            override fun cuisineClicked(recipe: Recipe) {
                recipeNewFragForwarding(recipe)
            }

            override fun likeClicked(recipe: Recipe) {
                viewModel.likeById(recipe.id)
            }

            override fun editClicked(recipe: Recipe) {
                viewModel.fixRecipe(recipe)
                viewModel.upDateInstructions()
                newFragmentForwarding(recipe)
            }

            override fun removeClicked(recipe: Recipe) {
                viewModel.deleteById(recipe.id)
            }
        })

        binding.listOfRecipes.adapter = appAdapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            if (recipes.isNotEmpty()) {
                appAdapter.submitList(recipes)

                binding.root.findViewById<TextView>(R.id.emptyList).visibility =
                    View.INVISIBLE
                binding.root.findViewById<RecyclerView>(R.id.listOfRecipes).visibility = View.VISIBLE
            } else {
                binding.root.findViewById<TextView>(R.id.emptyList).visibility =
                    View.VISIBLE
                binding.root.findViewById<RecyclerView>(R.id.listOfRecipes).visibility = View.INVISIBLE
            }
        }



        return binding.root
    }
}