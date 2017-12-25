package com.pwr.mycookbook.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.pwr.mycookbook.model.model.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 02.12.2017.
 */
@Dao
public interface RecipeDao {

    @Insert
    long[] insertAll(Recipe... recipes);

    @Update
    void updateAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);

    @Query("SELECT * FROM recipes")
    Flowable<List<Recipe>> getAll();

    @Query("SELECT * FROM recipes WHERE id = :recipe_id")
    Maybe<Recipe> findById(long recipe_id);

    @Query("SELECT * FROM recipes WHERE category_id = :category_id")
    Flowable<List<Recipe>> findAllByCategory(long category_id);


}
