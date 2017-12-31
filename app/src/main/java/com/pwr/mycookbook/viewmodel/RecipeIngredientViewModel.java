package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;
import com.pwr.mycookbook.model.repository.RecipeIngredientLocalRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public class RecipeIngredientViewModel extends ViewModel{

    RecipeIngredientLocalRepository recipeIngredientLocalRepository;

    public RecipeIngredientViewModel(RecipeIngredientLocalRepository recipeIngredientLocalRepository) {
        this.recipeIngredientLocalRepository = recipeIngredientLocalRepository;
    }

    public void insertAll(Recipe_Ingredient... recipe_ingredients) {
        recipeIngredientLocalRepository.insertAll(recipe_ingredients);
    }

    public void update(Recipe_Ingredient... recipe_ingredients) {
        recipeIngredientLocalRepository.update(recipe_ingredients);
    }


    public void delete(Recipe_Ingredient recipe_ingredient) {
        recipeIngredientLocalRepository.delete(recipe_ingredient);
    }


    public Flowable<List<Ingredient>> getIngredientsForRecipe(long recipe_id) {
        return recipeIngredientLocalRepository.getIngredientsForRecipe(recipe_id);
    }

    public Flowable<List<Recipe_Ingredient>> getRecipeIngredients(long recipe_id) {
        return recipeIngredientLocalRepository.getRecipeIngredients(recipe_id);
    }


    public Flowable<List<Recipe_Ingredient>> getAll() {
        return recipeIngredientLocalRepository.getAll();
    }

    public void deleteRecipeWithIngredients(long recipe_id) {
        recipeIngredientLocalRepository.deleteRecipeWithIngredients(recipe_id);
    }

    public void deleteIngredientInRecipe(long recipe_id, long ingredient_id) {
        recipeIngredientLocalRepository.deleteIngredientInRecipe(recipe_id, ingredient_id);
    }
}
