package com.pwr.mycookbook.category_detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.add_edit_category.AddEditCategoryFragment;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.recepie_detail.RecipeDetailActivity;
import com.pwr.mycookbook.tables.*;

import java.io.Serializable;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoryDetailActivity extends AppCompatActivity
        implements CategoryDetailFragment.RecipesListListener{

    public static final String EXTRA_CATEGORY = "category";
    private Category category;
    private FragmentManager fm = getSupportFragmentManager();
    private CategoryDetailFragment categoryDetailFragment;
    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dossier_50));
        setSupportActionBar(toolbar);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        category = (Category) getIntent().getExtras().get(EXTRA_CATEGORY);
        getSupportActionBar().setSubtitle(category.getName());

        categoryDetailFragment = new CategoryDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(CategoryDetailFragment.EXTRA_CATEGORY, category);
        categoryDetailFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.category_detail_fragment, categoryDetailFragment)
                .commit();
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
                db.categoryDao().delete(category);
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