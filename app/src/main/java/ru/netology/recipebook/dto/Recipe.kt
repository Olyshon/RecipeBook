package ru.netology.recipebook.dto

data class Recipe (
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val author: String,
    val ingredients: String,
    val steps: String,
    val liked: Boolean = false,
    val mainPhoto: String?
)