package ru.netology.recipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipebook.R
import ru.netology.recipebook.activity.NewRecipeFragment.Companion.REQUEST_KEY
import ru.netology.recipebook.adapter.RecipesAdapter
import ru.netology.recipebook.databinding.FragmentFilterSwBinding
import ru.netology.recipebook.databinding.FragmentNewRecipeBinding
import ru.netology.recipebook.viewModel.RecipeViewModel

class FiltersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterSwBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipesAdapter(viewModel)

        viewModel.clearCategories()

        binding.ok.setOnClickListener {
            with(binding) {
                if (!(switchAmerican.isChecked || switchAsian.isChecked || switchEastern.isChecked || switchEuropean.isChecked ||
                            switchPanasian.isChecked || switchRussian.isChecked || switchMediterranean.isChecked)
                ) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.filterError),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            }
          var curCategory = ""
           if ( binding.switchAmerican.isChecked) {
               curCategory = binding.switchAmerican.text.toString()
               viewModel.selectedCategories.add(curCategory)
           }
            if ( binding.switchAsian.isChecked) {
                curCategory = binding.switchAsian.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            if ( binding.switchEastern.isChecked) {
                curCategory = binding.switchEastern.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            if ( binding.switchEuropean.isChecked) {
                curCategory = binding.switchEuropean.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            if ( binding.switchPanasian.isChecked) {
                curCategory = binding.switchPanasian.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            if ( binding.switchRussian.isChecked) {
                curCategory = binding.switchRussian.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            if ( binding.switchMediterranean.isChecked) {
                curCategory = binding.switchMediterranean.text.toString()
                viewModel.selectedCategories.add(curCategory)
            }
            println(viewModel.selectedCategories.toString())
            viewModel.onFilterClicked(viewModel.selectedCategories)
            adapter.submitList(viewModel.dataFiltered.value)
            findNavController().navigateUp()
        }

        return binding.root
    }

}