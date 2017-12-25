package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pwr.mycookbook.model.repository.IngredientLocalRepository;

import javax.inject.Inject;

import dagger.internal.GwtIncompatible;

/**
 * Created by olaku on 25.12.2017.
 */

public class IngredientViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    IngredientLocalRepository ingredientLocalRepository;

    @Inject
    public IngredientViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(IngredientViewModel.class)) {
            return (T) new IngredientViewModel(ingredientLocalRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
