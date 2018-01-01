package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;

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

    @Query("SELECT * FROM recipes where modification_date < :modify_date")
    List<Recipe> getNotModified(long modify_date);

    @Query("SELECT * FROM recipes WHERE id = :recipe_id")
    Recipe findById(long recipe_id);

    @Query("SELECT * FROM recipes WHERE category_id = :category_id")
    List<Recipe> findAllByCategory(long category_id);


}
