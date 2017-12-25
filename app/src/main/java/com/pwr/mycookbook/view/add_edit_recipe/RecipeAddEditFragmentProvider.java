package com.pwr.mycookbook.view.add_edit_recipe;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */

@Module
public abstract class RecipeAddEditFragmentProvider {

    @ContributesAndroidInjector
    abstract AddEditRecipeFragment addEditRecipeFragment();

    @ContributesAndroidInjector
    abstract AddEditRecipeIngredientsFragment addEditRecipeIngredientsFragment();

    @ContributesAndroidInjector
    abstract AddEditRecipePreparationFragment addEditRecipePreparationFragment();
}
