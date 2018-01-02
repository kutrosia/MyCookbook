package com.pwr.mycookbook.data.dao;

import com.pwr.mycookbook.data.model.Category;
import com.pwr.mycookbook.data.model.Ingredient;
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

    @Query("SELECT * FROM ingredients where modification_date > :modify_date")
    List<Ingredient> getNotModified(long modify_date);

    @Query("SELECT * FROM ingredients where id LIKE  :ingredient_id")
    Ingredient findById(long ingredient_id);

    @Query("SELECT * FROM ingredients where name LIKE  :name")
    Ingredient findByName(String name);

    @Query("SELECT * FROM ingredients WHERE `key` = :key")
    Ingredient findByKey(String key);

    @Query("SELECT COUNT(*) from ingredients")
    int getCount();



}
