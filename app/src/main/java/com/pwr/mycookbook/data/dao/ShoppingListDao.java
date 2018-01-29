package com.pwr.mycookbook.data.dao;


import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model_db.ShoppingList;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface ShoppingListDao {

    @Insert
    long[] insertAll(ShoppingList... shoppingLists);

    @Update
    void update(ShoppingList ingredient);

    @Delete
    void delete(ShoppingList ingredient);

    @Query("SELECT * FROM shoppinglists")
    List<ShoppingList> getAll();

    @Query("SELECT * FROM shoppinglists where modification_date > :modify_date")
    List<ShoppingList> getNotModified(long modify_date);

    @Query("SELECT * FROM shoppinglists WHERE `key` = :key")
    ShoppingList findByKey(String key);

    @Query("SELECT * FROM shoppinglists where id LIKE  :shoppinglist_id")
    ShoppingList findById(long shoppinglist_id);

    @Query("SELECT COUNT(*) from shoppinglists")
    int getCount();

}
