package ru.netology.recipebook.data
import androidx.lifecycle.LiveData
import ru.netology.recipebook.dto.Recipe

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    fun likeById(recipeId: Int)
    fun save(recipe: Recipe)
    fun delete(recipeId: Int)
    fun getFavourites(): LiveData<List<Recipe>>
    fun getFiltered(selectedCategories : MutableList<String>): LiveData<List<Recipe>>
    fun searchTitle(searchQuery: String): List<Recipe>?
    fun onMoveRecipe(fromPosition: Int, toPosition: Int, list: List<Recipe>)

    companion object {
        const val NEW_RECIPE_ID = 0
        const val NEW_STEP_ID = 0
    }
}