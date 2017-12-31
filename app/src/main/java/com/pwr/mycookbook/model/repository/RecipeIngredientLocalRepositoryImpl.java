package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.RecipeIngredientDao;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;

/**
 * Created by olaku on 26.12.2017.
 */

public class RecipeIngredientLocalRepositoryImpl implements RecipeIngredientLocalRepository{

    private RecipeIngredientDao recipeIngredientDao;
    private Executor executor;

    public RecipeIngredientLocalRepositoryImpl(RecipeIngredientDao recipeIngredientDao, Executor executor) {
        this.recipeIngredientDao = recipeIngredientDao;
        this.executor = executor;
    }

    @Override
    public void insertAll(Recipe_Ingredient... recipe_ingredients) {
        recipeIngredientDao.insertAll(recipe_ingredients);
    }

    @Override
    public void update(Recipe_Ingredient... recipe_ingredients) {
        recipeIngredientDao.update(recipe_ingredients);
    }

    @Override
    public void delete(Recipe_Ingredient recipe_ingredient) {
        recipeIngredientDao.delete(recipe_ingredient);
    }

    @Override
    public Flowable<List<Ingredient>> getIngredientsForRecipe(long recipe_id) {
        return recipeIngredientDao.getIngredientsForRecipe(recipe_id);
    }

    @Override
    public Flowable<List<Recipe_Ingredient>> getRecipeIngredients(long recipe_id) {
        return recipeIngredientDao.getRecipeIngredients(recipe_id);
    }

    @Override
    public Flowable<List<Recipe_Ingredient>> getAll() {
        return recipeIngredientDao.getAll();
    }

    @Override
    public void deleteRecipeWithIngredients(long recipe_id) {
        recipeIngredientDao.deleteRecipeWithIngredients(recipe_id);
    }

    @Override
    public void deleteIngredientInRecipe(long recipe_id, long ingredient_id) {
        recipeIngredientDao.deleteIngredientInRecipe(recipe_id, ingredient_id);
    }
}
