package ru.netology.recipebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import ru.netology.recipebook.adapter.RecipeInteractionListener
import ru.netology.recipebook.data.RecipeRepository
import ru.netology.recipebook.data.RecipeRepository.Companion.NEW_RECIPE_ID
import ru.netology.recipebook.data.impl.RecipeRepositoryImpl
import ru.netology.recipebook.db.AppDb
import ru.netology.recipebook.dto.Recipe
import ru.netology.recipebook.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application),
    RecipeInteractionListener {


    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).recipeDao
    )

    val data by repository::data

    val editEvent = SingleLiveEvent<Int?>()
    val oneRecipeEvent = SingleLiveEvent<Recipe>()
    val addRecipeEvent = SingleLiveEvent<Int?>()

    private val currentRecipe = MutableLiveData<Recipe?>(null)

    fun onAddButtonClicked(updateRecipe: Recipe) {

        val recipe = currentRecipe.value?.copy(
            title = updateRecipe.title,
            description = updateRecipe.description,
            category = updateRecipe.category,
            author = updateRecipe.author,
            ingredients = updateRecipe.ingredients,
            steps = updateRecipe.steps,
            liked = updateRecipe.liked,
            mainPhoto =  updateRecipe.mainPhoto

        ) ?: Recipe(
            id = NEW_RECIPE_ID,
            title = updateRecipe.title,
            description = updateRecipe.description,
            category = updateRecipe.category,
            author = updateRecipe.author,
            ingredients = updateRecipe.ingredients,
            steps = updateRecipe.steps,
            liked = updateRecipe.liked,
            mainPhoto =   updateRecipe.mainPhoto
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    override fun onLikeClicked(recipe: Recipe) =
        repository.likeById(recipe.id)


    override fun onRemoveClicked(recipe: Recipe) =
        repository.delete(recipe.id)

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        editEvent.value = recipe.id
    }

    override fun cancelEditing() {
        currentRecipe.value = null
        return
    }

    override fun onRecipeClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        oneRecipeEvent.value = recipe
    }

    override fun onFilterClicked(category: String) {
        repository.getFiltered(category)
        println("вызываю фильтрацию")
    }

    override fun onFavouriteClicked() {
        println("вызываю избранное")
        repository.getFavourites()
    }


}