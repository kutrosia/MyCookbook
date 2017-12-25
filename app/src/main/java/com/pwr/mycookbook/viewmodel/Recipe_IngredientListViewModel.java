package com.pwr.mycookbook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pwr.mycookbook.model.dao.Recipe_IngredientDao;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;
import com.pwr.mycookbook.model.repository.AppDatabase;

import java.util.List;

/**
 * Created by olaku on 20.12.2017.
 */

public class Recipe_IngredientListViewModel extends AndroidViewModel {

    LiveData<List<Recipe_Ingredient>> recipe_ingredients;
    LiveData<List<Ingredient>> ingredients;
    Recipe_IngredientDao recipe_ingredientDao;


    public Recipe_IngredientListViewModel(@NonNull Application application, long recipe_id) {
        super(application);

        recipe_ingredientDao = AppDatabase.getAppDatabase(application).recipe_ingredientDao();
        recipe_ingredients = recipe_ingredientDao.getRecipeIngredients(recipe_id);
        ingredients = recipe_ingredientDao.getIngredientsForRecipe(recipe_id);
    }

    public LiveData<List<Recipe_Ingredient>> getRecipe_ingredients() {
        return recipe_ingredients;
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void insertAll(Recipe_Ingredient... recipe_ingredients){
        recipe_ingredientDao.insertAll(recipe_ingredients);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final long recipe_id;

        public Factory(@NonNull Application application, long recipe_id) {
            this.application = application;
            this.recipe_id = recipe_id;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new Recipe_IngredientListViewModel(application, recipe_id);
        }
    }
}
