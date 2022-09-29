package ru.netology.recipebook.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: String,
    @ColumnInfo(name = "steps")
    val steps: String,
    @ColumnInfo(name = "liked")
    val liked: Boolean = false,
    @ColumnInfo(name = "mainPhoto")
    val mainPhoto: String?

)
