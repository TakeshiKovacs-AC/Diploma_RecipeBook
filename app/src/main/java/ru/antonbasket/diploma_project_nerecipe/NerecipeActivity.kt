package ru.antonbasket.diploma_project_nerecipe

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.antonbasket.diploma_project_nerecipe.databinding.ActivityNerecipeBinding
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentMainAppBinding
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.contentTextArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.cuisineArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeAuthorArguments
import ru.antonbasket.diploma_project_nerecipe.fragments.NewRecipeFragment.Companion.recipeNameArguments

class NerecipeActivity : AppCompatActivity(R.layout.activity_nerecipe) {
    private var clickedYorN = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val intentText = it.getStringExtra(Intent.EXTRA_TEXT)
            if (intentText?.isNotBlank() != true) {
                return@let
            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.navHostFragment).navigate(
                R.id.mainAppFragment_to_newRecipeFragment,
                Bundle().apply {
                    recipeAuthorArguments = "Виктор Баринов"
                    recipeNameArguments = "Шедевр!"
                    cuisineArguments = "Профессиональная кухня"
                    contentTextArguments = intentText
                }
            )
        }
    }

    override fun onOptionsItemSelected(point: MenuItem): Boolean {
        try {
            val binding = FragmentMainAppBinding.bind(findViewById(R.id.MainAppFragment))
            when (point.itemId) {
                R.id.search -> {
                    clickedYorN = !clickedYorN
                    if (clickedYorN) {
                        binding.topBar.visibility = View.VISIBLE
                        binding.groupSearch.visibility = View.VISIBLE
                        binding.groupCategory.visibility = View.GONE
                    } else {
                        binding.topBar.visibility = View.GONE
                        binding.groupSearch.visibility = View.GONE
                        binding.groupCategory.visibility = View.GONE
                    }
                }
                R.id.filter -> {
                    clickedYorN = !clickedYorN
                    if (clickedYorN) {
                        binding.topBar.visibility = View.VISIBLE
                        binding.groupSearch.visibility = View.GONE
                        binding.groupCategory.visibility = View.VISIBLE
                    } else {
                        binding.topBar.visibility = View.GONE
                        binding.groupSearch.visibility = View.GONE
                        binding.groupCategory.visibility = View.GONE
                    }
                }
            }
        } catch (e: Exception) {
            val showSnackBar: View? = this.currentFocus?.rootView
            if (showSnackBar != null) {
                Snackbar.make(
                    showSnackBar, R.string.searchError,5000
                )
                    .setAction(android.R.string.ok) {}
                    .show()
            }
        }
        return super.onOptionsItemSelected(point)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}