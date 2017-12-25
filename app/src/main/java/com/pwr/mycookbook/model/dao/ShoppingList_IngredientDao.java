package com.pwr.mycookbook.model.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.ShoppingList_Ingredient;

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

    @Query("SELECT * FROM ingredients INNER JOIN shoppinglists_ingredients ON " +
            "ingredients.id = shoppinglists_ingredients.ingredient_id " +
            "WHERE shoppinglists_ingredients.shoppinglist_id=:shoppinglist_id")
    LiveData<Ingredient> getIngredientsForShoppinglist(final long shoppinglist_id);

    @Query("SELECT * FROM shoppinglists_ingredients")
    LiveData<ShoppingList_Ingredient> getAll();

    @Query("DELETE from shoppinglists_ingredients where shoppinglist_id LIKE  :shoppinglist_id")
    void deleteShoppinglistWithIngredients(final long shoppinglist_id);

    @Query("DELETE from shoppinglists_ingredients where " +
            "shoppinglist_id = :shoppinglist_id and ingredient_id = :ingredient_id")
    void deleteIngredientInShoppinglist(final long shoppinglist_id, final long ingredient_id);

}
