package com.pwr.mycookbook.model.dao;

import com.pwr.mycookbook.model.model.Ingredient;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface IngredientDao {

    @Insert
    long[] insertAll(Ingredient... ingredients);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredients")
    Flowable<List<Ingredient>> getAll();

    @Query("SELECT * FROM ingredients where id LIKE  :ingredient_id")
    Maybe<Ingredient> findById(long ingredient_id);



    @Query("SELECT COUNT(*) from ingredients")
    int countCategories();



}
