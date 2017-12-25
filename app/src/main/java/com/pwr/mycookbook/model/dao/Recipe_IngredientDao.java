package com.pwr.mycookbook.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface Recipe_IngredientDao {

    @Insert
    void insertAll(Recipe_Ingredient... recipe_ingredients);

    @Update
    void update(Recipe_Ingredient... recipe_ingredients);

    @Delete
    void delete(Recipe_Ingredient recipe_ingredient);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM ingredients INNER JOIN recipes_ingredients ON " +
            "ingredients.id = recipes_ingredients.ingredient_id " +
            "WHERE recipes_ingredients.recipe_id=:recipe_id")
    LiveData<List<Ingredient>> getIngredientsForRecipe(final long recipe_id);

    @Query("SELECT * FROM recipes_ingredients " +
            "WHERE recipe_id=:recipe_id")
    LiveData<List<Recipe_Ingredient>> getRecipeIngredients(final long recipe_id);

    @Query("SELECT * FROM recipes_ingredients")
    LiveData<List<Recipe_Ingredient>> getAll();

    @Query("DELETE from recipes_ingredients where recipe_id LIKE  :recipe_id")
    void deleteRecipeWithIngredients(final long recipe_id);

    @Query("DELETE from recipes_ingredients where " +
            "recipe_id = :recipe_id and ingredient_id = :ingredient_id")
    void deleteIngredientInRecipe(final long recipe_id, final long ingredient_id);

}
