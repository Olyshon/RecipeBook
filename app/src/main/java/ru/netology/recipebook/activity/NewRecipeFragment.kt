package ru.netology.recipebook.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.recipebook.R
import ru.netology.recipebook.databinding.FragmentNewRecipeBinding
import ru.netology.recipebook.dto.Recipe
import ru.netology.recipebook.util.IntArg
import ru.netology.recipebook.util.StringArg
import ru.netology.recipebook.viewModel.RecipeViewModel


class NewRecipeFragment : Fragment() {

    private val categoryArray = arrayOf(
        "Азиатская",
        "Американская",
        "Восточная",
        "Европейская",
        "Паназиатская",
        "Русская",
        "Средиземноморская"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewRecipeBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        (requireActivity() as? AppActivity)?.hideBottomNav()

        var recipe: Recipe
        var curLike = false
        var src = ""
        val curId = arguments?.idArg

        if (curId != null) {
            viewModel.data.observe(viewLifecycleOwner) { recipes ->
                recipe = recipes.find { it.id == curId } ?: run {
                    findNavController().navigateUp()
                    return@observe
                }
                curLike = recipe.liked
                with(binding) {
                    editTitle.setText(recipe.title)
                    editDescription.setText(recipe.description)
                    src = Uri.parse(recipe.mainPhoto).toString()
                    image.setImageURI(src.toUri())
                    spinnerCategory.setSelection(numberCategory(recipe.category))
                    editIngridients.setText(recipe.ingredients)
                    editSteps.setText(recipe.steps)
                    editAuthor.setText(recipe.author)
                }
            }
        }
        binding.editTitle.requestFocus()

        val image = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            requireActivity().contentResolver.takePersistableUriPermission(
                requireNotNull(it),
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            src = it.toString()

            binding.image.setImageURI(it)
        }

        binding.addImage.setOnClickListener {
            image.launch(arrayOf("image/*"))
        }


        binding.saveRecipe.setOnClickListener {
            if (curId == null && binding.editTitle.text.isNullOrBlank() && binding.editIngridients.text.isNullOrBlank() &&
                binding.editAuthor.text.isNullOrBlank() && binding.editSteps.text.isNullOrBlank()
            ) {
                findNavController().navigateUp()
            }

            val updateRecipe = Recipe(
                id = curId ?: 0,
                title = binding.editTitle.text.toString(),
                description = binding.editDescription.text.toString(),
                category = binding.spinnerCategory.selectedItem.toString(),
                author = binding.editAuthor.text.toString(),
                ingredients = binding.editIngridients.text.toString(),
                steps = binding.editSteps.text.toString(),
                liked = curLike,
                mainPhoto = src
            )


            if (updateRecipe.title.isBlank() || updateRecipe.ingredients.isBlank() || updateRecipe.author.isBlank() || updateRecipe.steps.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.addRecipeError),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            viewModel.onAddButtonClicked(updateRecipe)
            findNavController().navigateUp()
        }

        return binding.root

    }

    override fun onDestroy() {
        (requireActivity() as? AppActivity)?.showBottomNav()
        super.onDestroy()
    }

    private fun numberCategory(str: String): Int {
        for (i in categoryArray.indices)
            if (str == categoryArray[i]) return i
        return -1
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"

        var Bundle.textArg: String? by StringArg
        var Bundle.idArg: Int? by IntArg
    }
}