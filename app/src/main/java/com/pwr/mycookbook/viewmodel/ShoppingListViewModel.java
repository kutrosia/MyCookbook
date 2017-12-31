package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.pwr.mycookbook.model.model.ShoppingList;
import com.pwr.mycookbook.model.repository.ShoppingListLocalRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 26.12.2017.
 */

public class ShoppingListViewModel extends ViewModel {

    private ShoppingListLocalRepository shoppingListLocalRepository;

    public ShoppingListViewModel(ShoppingListLocalRepository shoppingListLocalRepository) {
        this.shoppingListLocalRepository = shoppingListLocalRepository;
    }

    public void insertAll(ShoppingList... shoppingLists) {
        shoppingListLocalRepository.insertAll(shoppingLists);
    }

    public void update(ShoppingList shoppingList) {
        shoppingListLocalRepository.update(shoppingList);
    }

    public void delete(ShoppingList shoppingList) {
        shoppingListLocalRepository.delete(shoppingList);
    }

    public Flowable<List<ShoppingList>> getAll() {
        return shoppingListLocalRepository.getAll();
    }

    public Maybe<ShoppingList> findById(long shoppinglist_id) {
        return shoppingListLocalRepository.findById(shoppinglist_id);
    }
}
