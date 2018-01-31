package com.pwr.mycookbook.ui.category_detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.service_db.CategoryRepository;
import com.pwr.mycookbook.ui.add_edit_category.AddEditCategoryFragment;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.ui.recepie_detail.RecipeDetailActivity;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoryDetailActivity extends AppCompatActivity
        implements CategoryDetailFragment.RecipesListListener{

    public static final String EXTRA_CATEGORY = "category";
    private Category category;
    private FragmentManager fm = getSupportFragmentManager();
    private CategoryDetailFragment categoryDetailFragment;
    private CategoryRepository categoryRepository;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_category_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        categoryRepository = new CategoryRepository(getApplicationContext());
        category = (Category) getIntent().getExtras().get(EXTRA_CATEGORY);
        getSupportActionBar().setSubtitle(category.getName());

        categoryDetailFragment = CategoryDetailFragment.newInstance(category);
    }

    private void applyStyle() {
        String color = sharedPreferences.getString(SettingsActivity.KEY_APPEARANCE_COLOR, "");
        switch (color){
            case "1":
                getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case "2":
                getTheme().applyStyle(R.style.OverlayPrimaryColorGreen, true);
                break;
            case "3":
                getTheme().applyStyle(R.style.OverlayPrimaryColorBlue, true);
                break;
            case "4":
                getTheme().applyStyle(R.style.OverlayPrimaryColorRed, true);
                break;
        }
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
                AddEditCategoryFragment addEditCategoryFragment = AddEditCategoryFragment.newInstance(category);
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
                categoryRepository.delete(category);
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
}
