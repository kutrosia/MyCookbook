package com.pwr.mycookbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mindorks.placeholderview.PlaceHolderView;
import com.pwr.mycookbook.add_edit_category.AddEditCategoryFragment;
import com.pwr.mycookbook.lists.CategoriesListFragment;
import com.pwr.mycookbook.category_detail.CategoryDetailActivity;
import com.pwr.mycookbook.ingredient.AddEditIngredientFragment;
import com.pwr.mycookbook.lists.IngredientsListFragment;
import com.pwr.mycookbook.add_edit_recipe.AddEditRecipeActivity;
import com.pwr.mycookbook.lists.PagerAdapter;
import com.pwr.mycookbook.navigation_drawer.DrawerHeader;
import com.pwr.mycookbook.navigation_drawer.DrawerMenuItem;
import com.pwr.mycookbook.recepie_detail.RecipeDetailActivity;
import com.pwr.mycookbook.lists.RecipesListFragment;
import com.pwr.mycookbook.tables.*;

/**
 * Created by olaku on 22.11.2017.
 */

public class MainActivity extends AppCompatActivity implements
        RecipesListFragment.RecipesListListener,
        CategoriesListFragment.CategoryListListener,
        IngredientsListFragment.IngredientsListListener {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private SQLiteDatabase db;
    private TabLayout tabLayout;
    private FragmentManager fm = getSupportFragmentManager();

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerView = findViewById(R.id.drawerView);
        mToolbar = findViewById(R.id.toolbar);
        mGalleryView = findViewById(R.id.galleryView);
        setupDrawer();

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(new CategoriesListFragment(), "Kategorie");
        pagerAdapter.addFragment(new RecipesListFragment(), "Przepisy");
        pagerAdapter.addFragment(new IngredientsListFragment(), "Składniki");

        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupDrawer() {
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_RECIPES))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PORTIONS_COUNTER))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SHOPPING_LISTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TIMER))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SYNC))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_VIDEOS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_OPTIONS));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch(item.getItemId()){
            case R.id.action_add_category:
                AddEditCategoryFragment addEditCategoryFragment = AddEditCategoryFragment.newInstance(null);
                addEditCategoryFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        viewPager.setAdapter(pagerAdapter);
                    }
                });
                addEditCategoryFragment.show(fm, "New category");
                break;
            case R.id.action_add_recipe:
                Recipe recipe = new Recipe();
                recipe.setNew(true);
                intent = new Intent(this, AddEditRecipeActivity.class);
                intent.putExtra(AddEditRecipeActivity.EXTRA_RECIPE, recipe);
                startActivity(intent);
                break;
            case R.id.action_add_ingredient:
                AddEditIngredientFragment addEditIngredientFragment = AddEditIngredientFragment.newInstance(null);
                addEditIngredientFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        viewPager.setAdapter(pagerAdapter);
                        pagerAdapter.getItem(2);

                    }
                });
                addEditIngredientFragment.show(fm, "New ingredient");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recipeItemClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(intent);
    }


    @Override
    public void categoryItemClicked(Category category) {
        Intent intent = new Intent(this, CategoryDetailActivity.class);
        intent.putExtra(CategoryDetailActivity.EXTRA_CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void ingredientItemClicked(Ingredient ingredient) {
        AddEditIngredientFragment addEditIngredientFragment = AddEditIngredientFragment.newInstance(ingredient);
        addEditIngredientFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                int id = viewPager.getCurrentItem();
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(id);
                pagerAdapter.getItem(2);

            }
        });
        addEditIngredientFragment.show(fm, "Nowy składnik");
    }
}
