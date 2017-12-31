package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.pwr.mycookbook.model.repository.ShoppingListLocalRepository;

import javax.inject.Inject;

/**
 * Created by olaku on 26.12.2017.
 */

public class ShoppingListViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    ShoppingListLocalRepository localRepository;

    @Inject
    public ShoppingListViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingListViewModel.class)) {
            return (T) new ShoppingListViewModel(localRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
