package ru.netology.recipebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RecipeEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
   abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, AppDb::class.java, "recipe.db"
            ).allowMainThreadQueries()  // не стоит включать базу и выполнять запросы в основном потоке, но сейчас так
                .build()
    }
}