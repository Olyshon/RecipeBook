package ru.netology.recipebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

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

    private val currentRecipe = MutableLiveData<Recipe?>(null)
    var selectedCategories = mutableListOf<String>()

    val dataFavorites = repository.getFavourites()
    var dataFiltered = Transformations.distinctUntilChanged(data) as MutableLiveData<List<Recipe>>

    fun onAddButtonClicked(updateRecipe: Recipe) {

        val recipe = currentRecipe.value?.copy(
            title = updateRecipe.title,
            description = updateRecipe.description,
            category = updateRecipe.category,
            author = updateRecipe.author,
            ingredients = updateRecipe.ingredients,
            steps = updateRecipe.steps,
            liked = updateRecipe.liked,
            mainPhoto = updateRecipe.mainPhoto

        ) ?: Recipe(
            id = NEW_RECIPE_ID,
            title = updateRecipe.title,
            description = updateRecipe.description,
            category = updateRecipe.category,
            author = updateRecipe.author,
            ingredients = updateRecipe.ingredients,
            steps = updateRecipe.steps,
            liked = updateRecipe.liked,
            mainPhoto = updateRecipe.mainPhoto
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

    override fun clearCategories() {
        selectedCategories.clear()
        return
    }

    override fun onRecipeClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        oneRecipeEvent.value = recipe
    }

    override fun onFilterClicked(selectedCategories: MutableList<String>) {
        dataFiltered = repository.getFiltered(selectedCategories) as MutableLiveData<List<Recipe>>
    }

    override fun onSearchClicked(query: String) {
        dataFiltered.value = repository.searchTitle("%$query%")

    }

    override fun onFavouriteClicked() {
        repository.getFavourites()
    }

    override fun onMove(fromPosition: Int, toPosition: Int, list: List<Recipe>) {
        repository.onMoveRecipe(fromPosition, toPosition, list)
    }
}