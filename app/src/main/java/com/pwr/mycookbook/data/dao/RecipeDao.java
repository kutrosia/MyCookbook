package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model_db.Recipe;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */
@Dao
public interface RecipeDao {

    @Insert
    long[] insertAll(Recipe... recipes);

    @Update
    void updateAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes where modification_date > :modify_date")
    List<Recipe> getNotModified(long modify_date);

    @Query("SELECT * FROM recipes WHERE id = :recipe_id")
    Recipe findById(long recipe_id);

    @Query("SELECT * FROM recipes WHERE `key` = :key")
    Recipe findByKey(String key);

    @Query("SELECT * FROM recipes WHERE category_id = :category_id")
    List<Recipe> findAllByCategory(long category_id);

    @Query("SELECT * FROM recipes ORDER BY category_id")
    List<Recipe> filterByCategory();

    @Query("SELECT * FROM recipes ORDER BY title")
    List<Recipe> filterByTitle();

    @Query("SELECT * FROM recipes ORDER BY time")
    List<Recipe> filterByTime();

    @Query("SELECT * FROM recipes WHERE title LIKE :word1 OR title LIKE :word2 OR title LIKE :word3")
    List<Recipe> getAllWithSubstring(String word1, String word2, String word3);

    @Query("SELECT * FROM recipes INNER JOIN recipes_ingredients ON " +
            "recipes.id = recipes_ingredients.recipe_id " +
            "WHERE recipes_ingredients.name LIKE :ingredient1 OR recipes_ingredients.name LIKE :ingredient2 OR recipes_ingredients.name LIKE :ingredient3")
    List<Recipe> getAllWithIngredients(String ingredient1, String ingredient2, String ingredient3);

    @Query("SELECT COUNT(*) from recipes")
    int getCount();



}
