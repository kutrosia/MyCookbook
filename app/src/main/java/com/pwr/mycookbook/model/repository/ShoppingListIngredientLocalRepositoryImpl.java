package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.ShoppingListIngredientDao;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.ShoppingList_Ingredient;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public class ShoppingListIngredientLocalRepositoryImpl implements ShoppingListIngredientLocalRepository {
    private ShoppingListIngredientDao shoppingListDao;
    private Executor executor;

    public ShoppingListIngredientLocalRepositoryImpl(ShoppingListIngredientDao shoppingListDao, Executor executor) {
        this.shoppingListDao = shoppingListDao;
        this.executor = executor;
    }

    @Override
    public void insertAll(ShoppingList_Ingredient... shoppinglist_ingredients) {
        shoppingListDao.insertAll(shoppinglist_ingredients);
    }

    @Override
    public void update(ShoppingList_Ingredient... shoppinglist_ingredients) {
        shoppingListDao.update(shoppinglist_ingredients);
    }

    @Override
    public void delete(ShoppingList_Ingredient shoppinglist_ingredients) {
        shoppingListDao.delete(shoppinglist_ingredients);
    }

    @Override
    public Maybe<Ingredient> getIngredientsForShoppinglist(long shoppinglist_id) {
        return shoppingListDao.getIngredientsForShoppinglist(shoppinglist_id);
    }

    @Override
    public Flowable<List<ShoppingList_Ingredient>> getAll() {
        return shoppingListDao.getAll();
    }

    @Override
    public void deleteShoppinglistWithIngredients(long shoppinglist_id) {
        shoppingListDao.deleteShoppinglistWithIngredients(shoppinglist_id);
    }

    @Override
    public void deleteIngredientInShoppinglist(long shoppinglist_id, long ingredient_id) {
        shoppingListDao.deleteIngredientInShoppinglist(shoppinglist_id, ingredient_id);
    }
}
