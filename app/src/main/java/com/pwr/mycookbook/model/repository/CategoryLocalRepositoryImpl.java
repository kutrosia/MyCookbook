package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.*;
import com.pwr.mycookbook.model.model.Category;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 19.12.2017.
 */

public class CategoryLocalRepositoryImpl implements CategoryLocalRepository {
    private CategoryDao categoryDao;
    private Executor executor;

    public CategoryLocalRepositoryImpl(CategoryDao categoryDao, Executor executor) {
        this.categoryDao = categoryDao;
        this.executor = executor;
    }

    @Override
    public Flowable<List<Category>> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Maybe<Category> findById(long category_id) {
        return categoryDao.findById(category_id);
    }

    @Override
    public void insert(final Category category) {
        executor.execute(() -> {
            categoryDao.insertAll(category);
        });
    }

    @Override
    public void update(Category category) {
        executor.execute(() -> {
            categoryDao.update(category);
        });
    }

    @Override
    public void delete(Category category) {
        executor.execute(() -> {
            categoryDao.delete(category);
        });
    }
}
