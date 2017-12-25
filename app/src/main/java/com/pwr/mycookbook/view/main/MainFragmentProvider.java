package com.pwr.mycookbook.view.main;

import com.pwr.mycookbook.view.add_edit_ingredient.AddEditIngredientFragment;
import com.pwr.mycookbook.view.category_detail.CategoryDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */
@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector
    abstract CategoriesListFragment categoriesListFragment();

    @ContributesAndroidInjector
    abstract RecipesListFragment recipesListFragment();

    @ContributesAndroidInjector
    abstract IngredientsListFragment ingredientsListFragment();

    @ContributesAndroidInjector
    abstract AddEditIngredientFragment addEditIngredientFragment();

}
