package ru.netology.recipebook.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.recipebook.data.RecipeRepository
import ru.netology.recipebook.db.RecipeDao
import ru.netology.recipebook.db.toEntity
import ru.netology.recipebook.db.toModel
import ru.netology.recipebook.dto.Recipe

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {


    override val data = dao.getAll().map{ entities ->
        entities.map{it.toModel()}
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity())
        else return  dao.update(recipe.toEntity())
    }

    override fun likeById(recipeId: Int) {
       dao.likeById(recipeId)
    }

    override fun delete(recipeId: Int) {
        dao.removeById(recipeId)
    }

    override fun getFavourites(): LiveData<List<Recipe>> {
    return dao.getFavourites().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getFiltered(selectedCategories : MutableList<String>): LiveData<List<Recipe>> {
        return dao.getFiltered(selectedCategories).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun searchTitle(searchQuery: String): List<Recipe>? {
        return dao.searchTitle(searchQuery)
    }

    override fun onMoveRecipe(fromPosition: Int, toPosition: Int, list: List<Recipe>) {
//        if (fromPosition < toPosition) {
//            val toOrder = list[toPosition].sorting
//            for (index in toPosition downTo fromPosition + 1) {
//                val recipe = list[index]
//                save(recipe.copy(sorting = list[index - 1].sorting))
//            }
//            save(list[fromPosition].copy(sorting = toOrder))
//        } else {
//            val fromOrder = list[toPosition].sorting
//            for (index in toPosition until fromPosition) {
//                val recipe = list[index]
//                save(recipe.copy(sorting = list[index + 1].sorting))
//            }
//            save(list[fromPosition].copy(sorting = fromOrder))
//        }
    }
}