package ru.netology.recipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipebook.R
import ru.netology.recipebook.activity.OneRecipeFragment.Companion.idArg
import ru.netology.recipebook.adapter.RecipesAdapter
import ru.netology.recipebook.databinding.FragmentFavoritesBinding
import ru.netology.recipebook.viewModel.RecipeViewModel

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter

        viewModel.dataFavorites.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noContent.visibility = View.VISIBLE
            } else {
                binding.noContent.visibility = View.GONE
                adapter.submitList(it)
            }
        }

        viewModel.editEvent.observe(viewLifecycleOwner) { recipeId ->
            findNavController().navigate(
                R.id.action_favouritesFragment_to_newRecipeFragment,
                Bundle().apply { idArg = recipeId })
        }

        viewModel.oneRecipeEvent.observe(viewLifecycleOwner) { recipe ->
            findNavController().navigate(
                R.id.action_favouritesFragment_to_oneRecipeFragment,
                Bundle().apply { idArg = recipe.id })
        }

        return binding.root
    }
}

