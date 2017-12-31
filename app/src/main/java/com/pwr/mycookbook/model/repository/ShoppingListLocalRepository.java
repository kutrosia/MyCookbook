package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.model.ShoppingList;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public interface ShoppingListLocalRepository {

    void insertAll(ShoppingList... shoppingLists);

    void update(ShoppingList shoppingList);

    void delete(ShoppingList shoppingList);

    Flowable<List<ShoppingList>> getAll();

    Maybe<ShoppingList> findById(long shoppinglist_id);
}
