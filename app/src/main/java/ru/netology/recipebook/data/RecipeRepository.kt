package ru.netology.recipebook.data
import androidx.lifecycle.LiveData
import ru.netology.recipebook.dto.Recipe

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    fun likeById(recipeId: Int)
    fun save(recipe: Recipe)
    fun delete(recipeId: Int)
    fun getFavourites(): LiveData<List<Recipe>>
    fun getFiltered(category: String): LiveData<List<Recipe>>

    companion object {
        const val NEW_RECIPE_ID =0
    }
}