package ru.netology.recipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipebook.adapter.RecipesAdapter

import ru.netology.recipebook.R
import ru.netology.recipebook.databinding.FragmentOneRecipeBinding
import ru.netology.recipebook.util.IntArg
import ru.netology.recipebook.util.StringArg
import ru.netology.recipebook.viewModel.RecipeViewModel

class OneRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val binding = FragmentOneRecipeBinding.inflate(inflater, container, false)
        val viewHolder = RecipesAdapter.ViewHolder(binding.oneRecipeLayout, viewModel) ///

        val curId = arguments?.idArg

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == curId } ?: run {
                findNavController().navigateUp()
                return@observe
            }

            viewHolder.bind(recipe)
            binding.ingredientsLayOne.visibility = View.VISIBLE
            binding.ingredientsLayOne.text = recipe.ingredients
            binding.stepsLayOne.visibility = View.VISIBLE
            binding.stepsLayOne.text = recipe.steps

        }

        viewModel.editEvent.observe(viewLifecycleOwner) { recipeId ->
            findNavController().navigate(
                R.id.action_oneRecipeFragment_to_newRecipeFragment,
                Bundle().apply { idArg = recipeId })
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
        var Bundle.idArg: Int? by IntArg
    }

}