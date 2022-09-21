package ru.netology.recipebook.db

import ru.netology.recipebook.dto.Recipe


internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    title = title,
    description = description,
    category = category,
    author = author,
   ingredients = ingredients,
    steps = steps,
    liked = liked,
    mainPhoto = mainPhoto
)
internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    author = author,
    ingredients = ingredients,
    steps = steps,
    liked = liked,
    mainPhoto = mainPhoto
)