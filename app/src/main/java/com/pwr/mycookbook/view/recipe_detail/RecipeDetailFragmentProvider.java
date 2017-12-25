package com.pwr.mycookbook.view.recipe_detail;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */

@Module
public abstract class RecipeDetailFragmentProvider {

    @ContributesAndroidInjector
    abstract RecipeDetailFragment recipeDetailFragment();

    @ContributesAndroidInjector
    abstract RecipeDetailIngredientsFragment recipeDetailIngredientsFragment();

    @ContributesAndroidInjector
    abstract RecipeDetailPreparationFragment recipeDetailPreparationFragment();
}
