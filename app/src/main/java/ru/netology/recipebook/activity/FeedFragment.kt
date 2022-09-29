package ru.netology.recipebook.activity

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ru.netology.recipebook.R
import ru.netology.recipebook.util.hideKeyboard
import ru.netology.recipebook.activity.OneRecipeFragment.Companion.idArg
import ru.netology.recipebook.adapter.RecipesAdapter
import ru.netology.recipebook.adapter.SimpleItemTouchHelperCallback
import ru.netology.recipebook.databinding.FragmentFeedBinding
import ru.netology.recipebook.viewModel.RecipeViewModel


class FeedFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                with(binding) {
                    recipesRecyclerView.visibility = View.GONE
                    noContent.visibility = View.VISIBLE
                }
            } else
                with(binding) {
                    recipesRecyclerView.visibility = View.VISIBLE
                    noContent.visibility = View.GONE
                    adapter.submitList(it)
                }
        }

        viewModel.dataFiltered.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                with(binding) {
                    recipesRecyclerView.visibility = View.GONE
                    noContent.visibility = View.VISIBLE
                }
            } else
                with(binding) {
                    recipesRecyclerView.visibility = View.VISIBLE
                    noContent.visibility = View.GONE
                    adapter.submitList(it)
                    viewModel.clearCategories()
                }
        }

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recipesRecyclerView)

        viewModel.cancelEditing()

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newRecipeFragment)
        }

        binding.searchButton.setOnClickListener {
            val query = binding.editSearch.text.toString()
            if (query.isBlank()) {
                adapter.submitList(viewModel.data.value)
            } else {
                viewModel.onSearchClicked(query)
                adapter.submitList(viewModel.dataFiltered.value)
            }
        }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_filterFragment)
        }

        viewModel.editEvent.observe(viewLifecycleOwner) { recipeId ->
            findNavController().navigate(
                R.id.action_feedFragment_to_newRecipeFragment,
                Bundle().apply { idArg = recipeId })
        }

        viewModel.oneRecipeEvent.observe(viewLifecycleOwner) { recipe ->
            findNavController().navigate(
                R.id.action_feedFragment_to_oneRecipeFragment,
                Bundle().apply { idArg = recipe.id })
        }

        return binding.root
    }


}



