package com.pwr.mycookbook.data.dao;


import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface ShoppingList_IngredientDao {

    @Insert
    void insertAll(ShoppingList_Ingredient... shoppinglist_ingredients);

    @Update
    void update(ShoppingList_Ingredient... shoppinglist_ingredients);

    @Delete
    void delete(ShoppingList_Ingredient shoppinglist_ingredients);

    @Query("SELECT * FROM shoppinglists_ingredients")
    List<ShoppingList_Ingredient> getAll();

    @Query("SELECT * FROM shoppinglists_ingredients WHERE shoppinglist_id LIKE :shoppinglist_id")
    List<ShoppingList_Ingredient> getIngredientsForShoppinglist(long shoppinglist_id);

    @Query("SELECT * FROM shoppinglists_ingredients where modification_date > :modify_date")
    List<ShoppingList_Ingredient> getNotModified(long modify_date);

    @Query("SELECT * FROM shoppinglists_ingredients WHERE `key` LIKE :key")
    ShoppingList_Ingredient findByKey(String key);

    @Query("DELETE from shoppinglists_ingredients where shoppinglist_id LIKE  :shoppinglist_id")
    void deleteShoppinglistWithIngredients(final long shoppinglist_id);

    @Query("DELETE from shoppinglists_ingredients where " +
            "shoppinglist_id LIKE :shoppinglist_id and ingredient_id LIKE :ingredient_id")
    void deleteIngredientInShoppinglist(final long shoppinglist_id, final long ingredient_id);

    @Query("SELECT COUNT(*) from shoppinglists_ingredients")
    int getCount();
}
