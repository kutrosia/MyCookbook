package com.pwr.mycookbook.view.category_detail;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by olaku on 24.12.2017.
 */

@Module
public abstract class CategoryDetailFragmentProvider {

    @ContributesAndroidInjector
    abstract CategoryDetailFragment categoryDetailFragment();
}
