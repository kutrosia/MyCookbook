package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.ShoppingList_Ingredient;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public interface ShoppingListIngredientLocalRepository {

    void insertAll(ShoppingList_Ingredient... shoppinglist_ingredients);

    void update(ShoppingList_Ingredient... shoppinglist_ingredients);

    void delete(ShoppingList_Ingredient shoppinglist_ingredients);

    Maybe<Ingredient> getIngredientsForShoppinglist(final long shoppinglist_id);

    Flowable<List<ShoppingList_Ingredient>> getAll();

    void deleteShoppinglistWithIngredients(final long shoppinglist_id);

    void deleteIngredientInShoppinglist(final long shoppinglist_id, final long ingredient_id);
}

