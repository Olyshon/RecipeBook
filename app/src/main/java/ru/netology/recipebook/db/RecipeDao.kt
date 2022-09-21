package ru.netology.recipebook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.netology.recipebook.dto.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Update
    fun update(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE liked ORDER BY id DESC")
    fun getFavourites(): LiveData<List<RecipeEntity>>

    @Query("""
        UPDATE recipes SET
        liked = CASE WHEN liked THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Int)


    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Int)

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY id DESC")
    fun getFiltered(category: String): LiveData<List<RecipeEntity>>








//    @Query("SELECT * FROM recipes WHERE `like` = 1 ORDER BY id DESC")
//    fun getAllFavorite(): LiveData<List<RecipeEntity>>

}