package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.pwr.mycookbook.model.repository.RecipeIngredientLocalRepository;

import javax.inject.Inject;

/**
 * Created by olaku on 26.12.2017.
 */

public class RecipeIngredientViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    RecipeIngredientLocalRepository localRepository;

    @Inject
    public RecipeIngredientViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeIngredientViewModel.class)) {
            return (T) new RecipeIngredientViewModel(localRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
