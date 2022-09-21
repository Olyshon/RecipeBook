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

    override fun getFiltered(category: String): LiveData<List<Recipe>> {
        return dao.getFiltered(category).map { entities ->
            entities.map { it.toModel() }
        }
    }
}