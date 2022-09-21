package ru.netology.recipebook.adapter

import ru.netology.recipebook.dto.Recipe


interface RecipeInteractionListener {
    fun onLikeClicked(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun cancelEditing()
    fun onRecipeClicked(recipe: Recipe)
    fun onFilterClicked(category: String)
    fun onFavouriteClicked()

}