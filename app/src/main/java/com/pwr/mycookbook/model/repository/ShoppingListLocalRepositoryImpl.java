package com.pwr.mycookbook.model.repository;

import com.pwr.mycookbook.model.dao.ShoppingListDao;
import com.pwr.mycookbook.model.model.ShoppingList;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public class ShoppingListLocalRepositoryImpl implements ShoppingListLocalRepository {
    private ShoppingListDao shoppingListDao;
    private Executor executor;

    public ShoppingListLocalRepositoryImpl(ShoppingListDao shoppingListDao, Executor executor) {
        this.shoppingListDao = shoppingListDao;
        this.executor = executor;
    }


    @Override
    public void insertAll(ShoppingList... shoppingLists) {
        shoppingListDao.insertAll(shoppingLists);
    }

    @Override
    public void update(ShoppingList shoppingList) {
        shoppingListDao.update(shoppingList);
    }

    @Override
    public void delete(ShoppingList shoppingList) {
        shoppingListDao.delete(shoppingList);
    }

    @Override
    public Flowable<List<ShoppingList>> getAll() {
        return shoppingListDao.getAll();
    }

    @Override
    public Maybe<ShoppingList> findById(long shoppinglist_id) {
        return shoppingListDao.findById(shoppinglist_id);
    }
}
