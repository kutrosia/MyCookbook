package com.pwr.mycookbook.dao;

import com.pwr.mycookbook.tables.Ingredient;
import android.arch.persistence.room.*;

import java.util.List;

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
    List<Ingredient> getAll();

    @Query("SELECT * FROM ingredients where id LIKE  :ingredient_id")
    Ingredient findById(long ingredient_id);

    @Query("SELECT COUNT(*) from ingredients")
    int countCategories();



}
