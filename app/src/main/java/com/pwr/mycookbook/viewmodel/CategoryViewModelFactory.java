package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.pwr.mycookbook.model.repository.CategoryLocalRepository;

import javax.inject.Inject;

/**
 * Created by olaku on 24.12.2017.
 */
public class CategoryViewModelFactory implements ViewModelProvider.Factory {


        @Inject
        CategoryLocalRepository localRepository;

        @Inject
        public CategoryViewModelFactory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
                return (T) new CategoryViewModel(localRepository);
            }
            throw new IllegalArgumentException("Wrong ViewModel class");
        }
}
