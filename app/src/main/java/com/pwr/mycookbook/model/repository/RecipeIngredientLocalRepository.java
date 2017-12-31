package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by olaku on 26.12.2017.
 */

public interface RecipeIngredientLocalRepository {


    void insertAll(Recipe_Ingredient... recipe_ingredients);

    void update(Recipe_Ingredient... recipe_ingredients);

    void delete(Recipe_Ingredient recipe_ingredient);

    Flowable<List<Ingredient>> getIngredientsForRecipe(final long recipe_id);

    Flowable<List<Recipe_Ingredient>> getRecipeIngredients(final long recipe_id);

    Flowable<List<Recipe_Ingredient>> getAll();

    void deleteRecipeWithIngredients(final long recipe_id);

    void deleteIngredientInRecipe(final long recipe_id, final long ingredient_id);
}
