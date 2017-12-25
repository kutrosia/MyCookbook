package com.pwr.mycookbook.di;

import com.pwr.mycookbook.view.add_edit_recipe.AddEditRecipeActivity;
import com.pwr.mycookbook.view.add_edit_recipe.RecipeAddEditFragmentProvider;
import com.pwr.mycookbook.view.category_detail.CategoryDetailActivity;
import com.pwr.mycookbook.view.category_detail.CategoryDetailFragmentProvider;
import com.pwr.mycookbook.view.main.MainActivity;
import com.pwr.mycookbook.view.main.MainFragmentProvider;
import com.pwr.mycookbook.view.recipe_detail.RecipeDetailActivity;
import com.pwr.mycookbook.view.recipe_detail.RecipeDetailFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = MainFragmentProvider.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = CategoryDetailFragmentProvider.class)
    abstract CategoryDetailActivity bindCategoryDetailActivity();

    @ContributesAndroidInjector(modules = RecipeDetailFragmentProvider.class)
    abstract RecipeDetailActivity bindRecipeDetailActivity();

    @ContributesAndroidInjector(modules = RecipeAddEditFragmentProvider.class)
    abstract AddEditRecipeActivity bindRecipeAddEditActivity();

}
