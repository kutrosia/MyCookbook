package com.pwr.mycookbook.view.add_edit_category;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */

@Module
public abstract class CategoryAddEditFragmentProvider {

    @ContributesAndroidInjector
    abstract AddEditCategoryFragment addEditCategoryFragment();
}
