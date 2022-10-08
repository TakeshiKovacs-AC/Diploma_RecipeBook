package ru.antonbasket.diploma_project_nerecipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.antonbasket.diploma_project_nerecipe.AppRecipeListUtils
import ru.antonbasket.diploma_project_nerecipe.R
import ru.antonbasket.diploma_project_nerecipe.StringArguments
import ru.antonbasket.diploma_project_nerecipe.databinding.FragmentNewInstructionsBinding
import ru.antonbasket.diploma_project_nerecipe.repositories.getUnrealPostId
import ru.antonbasket.diploma_project_nerecipe.view_model.AppViewModel
import ru.antonbasket.diploma_project_nerecipe.view_model.ExistingRecipeContent

class NewInstructionsFragment: Fragment() {
    private lateinit var instance: String
    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val initialViewModel: ExistingRecipeContent by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewInstructionsBinding.inflate(
            inflater,
            container,
            false
        )
        var testContentValue: String? = null
        val featuring = requireActivity().onBackPressedDispatcher.addCallback(this) {
            instance = binding.instructionsList.text.toString()
            if (::instance.isInitialized)
                initialViewModel.saveContent(instance)
            findNavController().navigateUp()
        }
        featuring.isEnabled = true


        binding.numberOfStep.text = "${resources.getString(R.string.stepNumber)}${arguments?.instrPlaceArguments?.toInt()}"
        arguments?.instrTitleArguments?.let(binding.stepInput::setText)
        arguments?.instrDescrArguments?.let(binding.instructionsList::setText)
            ?: binding.instructionsList.setText(
                initialViewModel.getRecById(getUnrealPostId())?.content
            ).let {
                testContentValue = it.toString()
            }

        binding.okButton.setOnClickListener {
            if (binding.stepInput.text.isNullOrBlank() ||
                binding.instructionsList.text.isNullOrBlank()
            ) {
                Snackbar.make(
                    binding.root, R.string.fieldsNotFilled,
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                    }
                    .show()
                return@setOnClickListener
            }

            if (!binding.stepInput.text.isNullOrBlank() ||
                !binding.instructionsList.text.isNullOrBlank()
            ) {
                if (testContentValue != null && viewModel.recipeContent.value?.id != 0L) {
                    viewModel.emptyContent()
                }
                viewModel.amendInstructionsValue(
                    arguments?.instrPlaceArguments.let { it?.toInt() } ?: 0,
                    binding.stepInput.text.toString(),
                    binding.instructionsList.text.toString()
                )
                viewModel.saveInstruction()
            }
            initialViewModel.saveContent("")
            AppRecipeListUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
    companion object {
        var Bundle.newInstrArguments: String? by StringArguments
        var Bundle.instrPlaceArguments: String? by StringArguments
        var Bundle.instrTitleArguments: String? by StringArguments
        var Bundle.instrDescrArguments: String? by StringArguments
    }
}