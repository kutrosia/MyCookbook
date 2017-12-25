package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pwr.mycookbook.model.repository.RecipeLocalRepository;

import javax.inject.Inject;

/**
 * Created by olaku on 24.12.2017.
 */

public class RecipeViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    RecipeLocalRepository recipeLocalRepository;

    @Inject
    public RecipeViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeViewModel.class)) {
            return (T) new RecipeViewModel(recipeLocalRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
