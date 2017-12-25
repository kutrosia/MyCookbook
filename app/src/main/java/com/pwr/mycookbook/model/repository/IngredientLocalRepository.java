package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.model.Ingredient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 24.12.2017.
 */

public interface IngredientLocalRepository {

    Flowable<List<Ingredient>> getAll();
    Maybe<Ingredient> findById(long ingredient_id);
    void insert(Ingredient ingredient);
    void update(Ingredient ingredient);
    void delete(Ingredient ingredient);

}
