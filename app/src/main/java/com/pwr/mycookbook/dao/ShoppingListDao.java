package com.pwr.mycookbook.dao;


import android.arch.persistence.room.*;

import com.pwr.mycookbook.tables.Ingredient;
import com.pwr.mycookbook.tables.ShoppingList;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface ShoppingListDao {

    @Insert
    void insertAll(ShoppingList... shoppingLists);

    @Update
    void update(ShoppingList ingredient);

    @Delete
    void delete(ShoppingList ingredient);

    @Query("SELECT * FROM shoppinglists")
    List<ShoppingList> getAll();

    @Query("SELECT * FROM shoppinglists where id LIKE  :shoppinglist_id")
    ShoppingList findById(long shoppinglist_id);

}
