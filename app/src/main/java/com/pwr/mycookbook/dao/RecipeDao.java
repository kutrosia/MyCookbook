package com.pwr.mycookbook.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.tables.Recipe;

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

    @Query("SELECT * FROM recipes WHERE id = :recipe_id")
    Recipe findById(long recipe_id);

    @Query("SELECT * FROM recipes WHERE category_id = :category_id")
    List<Recipe> findAllByCategory(long category_id);


}
