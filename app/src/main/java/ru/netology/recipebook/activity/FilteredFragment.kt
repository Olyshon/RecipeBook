package ru.netology.recipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.recipebook.adapter.RecipesAdapter
import ru.netology.recipebook.databinding.FragmentFavoritesBinding
import ru.netology.recipebook.viewModel.RecipeViewModel

class FilteredFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val filteredRecipes = recipes.filter { it.category == "category" }
            adapter.submitList(filteredRecipes)

            if (filteredRecipes.isEmpty()) {
                binding.noContent.visibility = View.VISIBLE
            } else {
                binding.noContent.visibility = View.GONE
            }
        }
        return binding.root
    }
}