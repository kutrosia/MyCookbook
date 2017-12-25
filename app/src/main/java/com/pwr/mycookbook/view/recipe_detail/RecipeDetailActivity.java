package com.pwr.mycookbook.view.recipe_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;
import com.pwr.mycookbook.view.RecipePDF;
import com.pwr.mycookbook.view.main.PagerAdapter;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.view.add_edit_recipe.AddEditRecipeActivity;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;
import com.pwr.mycookbook.viewmodel.Recipe_IngredientListViewModel;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RecipeDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    public static final String EXTRA_RECIPE = "recipe";
    private Recipe mRecipe;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private FragmentManager fm = getSupportFragmentManager();
    private RecipeViewModel recipeViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    RecipeViewModelFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        setContentView(R.layout.activity_recipe_detail);

        recipeViewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dossier_50));
        setSupportActionBar(toolbar);

        long recipe_id = (long) getIntent().getExtras().get(EXTRA_RECIPE);
        setRecipe(recipe_id);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(RecipeDetailFragment.newInstance(recipe_id), "Szczegóły");
        pagerAdapter.addFragment(RecipeDetailIngredientsFragment.newInstance(recipe_id), "Składniki");
        pagerAdapter.addFragment(RecipeDetailPreparationFragment.newInstance(recipe_id), "Instrukcje");

        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setRecipe(long recipe_id){
        recipeViewModel.findById(recipe_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe ->
                    mRecipe = recipe
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_edit_recipe:
                mRecipe.setNew(false);
                intent = new Intent(this, AddEditRecipeActivity.class);
                intent.putExtra(AddEditRecipeActivity.EXTRA_RECIPE, mRecipe.getId());
                startActivity(intent);
                finish();
                break;
            case R.id.action_remove_recipe:
                recipeViewModel.delete(mRecipe);
                finish();
                break;
            case R.id.action_share:
                Recipe_IngredientListViewModel.Factory factory = new Recipe_IngredientListViewModel.Factory(getApplication(), mRecipe.getId());
                Recipe_IngredientListViewModel recipe_ingredientListViewModel = ViewModelProviders.of(this, factory).get(Recipe_IngredientListViewModel.class);
                List<Recipe_Ingredient> recipe_ingredientList = recipe_ingredientListViewModel.getRecipe_ingredients().getValue();
                List<Ingredient> ingredients = recipe_ingredientListViewModel.getIngredients().getValue();
                File file = new RecipePDF().writeRecipeToPDF(mRecipe, recipe_ingredientList, ingredients, getApplicationContext());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(Intent.createChooser(share, "Share document"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
