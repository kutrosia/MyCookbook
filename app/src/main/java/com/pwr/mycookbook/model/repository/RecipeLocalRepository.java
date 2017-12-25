package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.model.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 24.12.2017.
 */

public interface RecipeLocalRepository {

    Flowable<List<Recipe>> getAll();
    Flowable<List<Recipe>> findAllByCategory(long category_id);
    Maybe<Recipe> findById(long recipe_id);
    long[] insert(Recipe recipe);
    void update(Recipe recipe);
    void delete(Recipe recipe);
}
