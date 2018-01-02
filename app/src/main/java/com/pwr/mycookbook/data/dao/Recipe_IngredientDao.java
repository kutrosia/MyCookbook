package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model.Ingredient;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;

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

    @Query("SELECT * FROM ingredients INNER JOIN recipes_ingredients ON " +
            "ingredients.id = recipes_ingredients.ingredient_id " +
            "WHERE recipes_ingredients.recipe_id=:recipe_id")
            List<Recipe_Ingredient> getIngredientsForRecipe(final long recipe_id);

    @Query("SELECT * FROM recipes_ingredients")
    List<Recipe_Ingredient> getAll();

    @Query("SELECT * FROM recipes_ingredients where modification_date > :modify_date")
    List<Recipe_Ingredient> getNotModified(long modify_date);

    @Query("DELETE from recipes_ingredients where recipe_id LIKE  :recipe_id")
    void deleteRecipeWithIngredients(final long recipe_id);

    @Query("DELETE from recipes_ingredients where " +
            "recipe_id = :recipe_id and ingredient_id = :ingredient_id")
    void deleteIngredientInRecipe(final long recipe_id, final long ingredient_id);

}
