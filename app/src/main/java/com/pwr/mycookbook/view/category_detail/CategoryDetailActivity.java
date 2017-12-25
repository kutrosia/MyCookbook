package com.pwr.mycookbook.view.category_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.view.add_edit_category.AddEditCategoryFragment;
import com.pwr.mycookbook.view.recipe_detail.RecipeDetailActivity;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoryDetailActivity extends AppCompatActivity
        implements CategoryDetailFragment.RecipesListListener,
        HasSupportFragmentInjector{

    public static final String EXTRA_CATEGORY = "category";
    private Category category;
    private FragmentManager fm = getSupportFragmentManager();
    private CategoryDetailFragment categoryDetailFragment;
    private CategoryViewModel categoryViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    CategoryViewModelFactory factory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        categoryViewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
        setContentView(R.layout.activity_category_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dossier_50));
        setSupportActionBar(toolbar);

        long category_id = (Long) getIntent().getExtras().get(EXTRA_CATEGORY);

        /*CategoryViewModel.Factory factory = new CategoryViewModel.Factory(getApplication(), category_id);
        categoryViewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);
        categoryViewModel.getCategoryLiveData().observe(this, new Observer<Category>() {
            @Override
            public void onChanged(@Nullable Category cat) {
                if(cat!=null){
                    category = cat;
                    getSupportActionBar().setSubtitle(category.getName());
                }
            }
        });*/

        categoryDetailFragment = CategoryDetailFragment.newInstance(category_id);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit_category:
                AddEditCategoryFragment addEditCategoryFragment = AddEditCategoryFragment.newInstance(category.getId());
                addEditCategoryFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getSupportActionBar().setSubtitle(category.getName());
                        categoryDetailFragment.onResume();
                    }
                });
                addEditCategoryFragment.show(fm, "New category");
                break;
            case R.id.action_remove_category:
                //categoryViewModel.deleteCategory();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
