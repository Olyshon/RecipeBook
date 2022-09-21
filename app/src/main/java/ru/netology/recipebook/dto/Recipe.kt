package ru.netology.recipebook.dto

data class Recipe (
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val author: String,
    val ingredients: String, // возможно переделать в массив
    val steps: String, //потом надо сделать списком с возможной картинкой
    val liked: Boolean = false,
    val mainPhoto: String?

)