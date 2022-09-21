package ru.netology.recipebook.activity

import android.net.Uri
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
        //   val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.cancelEditing()
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newRecipeFragment)
        }

        binding.filterButton.setOnClickListener {
          //  отобразить фильтрованный список через запрос к бд? может через синглайфивент и обсерв??

            findNavController().navigate(R.id.action_feedFragment_to_filterFragment)
            //TODO" вернуть обратно список категорий для отображения и запустить?
        //  viewModel.data = viewModel.onFavouriteClicked()
            ////            viewModel.data.observe(viewLifecycleOwner) { recipes ->
            ////                adapter.submitList(recipes)
            ////            }"
        }


        viewModel.editEvent.observe(viewLifecycleOwner) { recipeId ->
            findNavController().navigate(
                R.id.action_feedFragment_to_newRecipeFragment,
                Bundle().apply { idArg = recipeId })

        }

        viewModel.oneRecipeEvent.observe(viewLifecycleOwner) { recipe -> // можно привести к одному виду с эдит ивент если заработает
            findNavController().navigate(
                R.id.action_feedFragment_to_oneRecipeFragment,
                Bundle().apply { idArg = recipe.id })
        }





        return binding.root
    }

    /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          // The usage of an interface lets you inject your own implementation
          val menuHost: MenuHost = requireActivity()

          // Add menu items without using the Fragment Menu APIs
          // Note how we can tie the MenuProvider to the viewLifecycleOwner
          // and an optional Lifecycle.State (here, RESUMED) to indicate when
          // the menu should be visible
          menuHost.addMenuProvider(object : MenuProvider {
              override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                  // Add menu items here
                  menuInflater.inflate(R.menu.menu_bottom_nav, menu)
              }

              override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                  // Handle the menu selection
                  return when (menuItem.itemId) {
  //                    R.id.menu_clear -> {
  //                        // clearCompletedTasks()
  //                        true
  //                    }
  //                    R.id.menu_refresh -> {
  //                        // loadTasks(true)
  //                        true
  //                    }
                      else -> false
                  }
              }
          }, viewLifecycleOwner, Lifecycle.State.RESUMED)
      }
  */
}



