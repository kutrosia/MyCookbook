package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.IngredientDao;
import com.pwr.mycookbook.model.model.Ingredient;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 24.12.2017.
 */

public class IngredientLocalRepositoryImpl implements IngredientLocalRepository{
    private IngredientDao ingredientDao;
    private Executor executor;

    public IngredientLocalRepositoryImpl(IngredientDao ingredientDao, Executor executor) {
        this.ingredientDao = ingredientDao;
        this.executor = executor;
    }


    @Override
    public Flowable<List<Ingredient>> getAll() {
        return ingredientDao.getAll();
    }

    @Override
    public Maybe<Ingredient> findById(long ingredient_id) {
        return ingredientDao.findById(ingredient_id);
    }

    @Override
    public void insert(Ingredient ingredient) {
        ingredientDao.insertAll(ingredient);
    }

    @Override
    public void update(Ingredient ingredient) {
        ingredientDao.update(ingredient);
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientDao.delete(ingredient);
    }
}
