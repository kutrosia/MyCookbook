package com.pwr.mycookbook.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.pwr.mycookbook.model.dao.CategoryDao;
import com.pwr.mycookbook.model.dao.IngredientDao;
import com.pwr.mycookbook.model.dao.RecipeDao;
import com.pwr.mycookbook.model.dao.Recipe_IngredientDao;
import com.pwr.mycookbook.model.repository.AppDatabase;
import com.pwr.mycookbook.model.repository.CategoryLocalRepository;
import com.pwr.mycookbook.model.repository.CategoryLocalRepositoryImpl;
import com.pwr.mycookbook.model.repository.IngredientLocalRepository;
import com.pwr.mycookbook.model.repository.IngredientLocalRepositoryImpl;
import com.pwr.mycookbook.model.repository.RecipeLocalRepository;
import com.pwr.mycookbook.model.repository.RecipeLocalRepositoryImpl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by olaku on 23.12.2017.
 */

@Module
public class AppModule {


    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return AppDatabase.getAppDatabase(application);
    }

    @Provides
    @Singleton
    CategoryDao categoryDao(AppDatabase db) {
        return db.categoryDao();
    }

    @Provides
    @Singleton
    RecipeDao recipeDao(AppDatabase db) {
        return db.recipeDao();
    }

    @Provides
    @Singleton
    IngredientDao ingredientDao(AppDatabase db) {
        return db.ingredientDao();
    }

    @Provides
    @Singleton
    Recipe_IngredientDao recipe_ingredientDao(AppDatabase db) {
        return db.recipe_ingredientDao();
    }

    @Provides
    @Singleton
    public CategoryLocalRepository getCategoryLocalRepository(CategoryDao categoryDao, Executor exec){
        return new CategoryLocalRepositoryImpl(categoryDao, exec);
    }

    @Provides
    @Singleton
    public RecipeLocalRepository getRecipeLocalRepository(RecipeDao recipeDao, Executor exec){
        return new RecipeLocalRepositoryImpl(recipeDao, exec);
    }

    @Provides
    @Singleton
    public IngredientLocalRepository getIngredientLocalRepository(IngredientDao ingredientDao, Executor exec){
        return new IngredientLocalRepositoryImpl(ingredientDao, exec);
    }

    @Singleton
    @Provides
    public Executor getExecutor(){
        return  Executors.newFixedThreadPool(2);
    }


}
