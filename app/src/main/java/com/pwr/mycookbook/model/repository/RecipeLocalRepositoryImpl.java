package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.RecipeDao;
import com.pwr.mycookbook.model.model.Recipe;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 24.12.2017.
 */

public class RecipeLocalRepositoryImpl implements RecipeLocalRepository {
    private RecipeDao recipeDao;
    private Executor executor;

    public RecipeLocalRepositoryImpl(RecipeDao recipeDao, Executor executor) {
        this.recipeDao = recipeDao;
        this.executor = executor;
    }


    @Override
    public Flowable<List<Recipe>> getAll() {
        return recipeDao.getAll();
    }

    @Override
    public Flowable<List<Recipe>> findAllByCategory(long category_id) {
        return recipeDao.findAllByCategory(category_id);
    }

    @Override
    public Maybe<Recipe> findById(long recipe_id) {
        return recipeDao.findById(recipe_id);
    }

    @Override
    public long[] insert(Recipe recipe) {
        return recipeDao.insertAll(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        recipeDao.updateAll(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeDao.deleteAll(recipe);
    }
}
