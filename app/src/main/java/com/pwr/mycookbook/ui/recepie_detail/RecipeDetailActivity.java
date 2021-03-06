package com.pwr.mycookbook.ui.recepie_detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pwr.mycookbook.data.file.RecipePDF;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;
import com.pwr.mycookbook.data.service_db.RecipeRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;
import com.pwr.mycookbook.ui.main.PagerAdapter;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.add_edit_recipe.AddEditRecipeActivity;
import com.pwr.mycookbook.ui.settings.SettingsActivity;
import com.pwr.mycookbook.ui.shoppinglist_detail.ShoppinglistDetailActivity;

import java.io.File;
import java.util.List;


public class RecipeDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "recipe";
    private Recipe recipe;
    private RecipeRepository recipeRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private FragmentManager fm = getSupportFragmentManager();
    private ShoppinglistRepository shoppinglistRepository;
    private ShoppinglistIngredientRepository shoppinglistIngredientRepository;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recipeRepository = new RecipeRepository(getApplicationContext());
        recipe = (Recipe) getIntent().getExtras().get(EXTRA_RECIPE);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(RecipeDetailFragment.newInstance(recipe), "Szczegóły");
        pagerAdapter.addFragment(RecipeDetailIngredientsFragment.newInstance(recipe), "Składniki");
        pagerAdapter.addFragment(RecipeDetailPreparationFragment.newInstance(recipe), "Instrukcje");

        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        getMenuInflater().inflate(R.menu.recipe_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_edit_recipe:
                recipe.setNew(false);
                intent = new Intent(this, AddEditRecipeActivity.class);
                intent.putExtra(AddEditRecipeActivity.EXTRA_RECIPE, recipe);
                startActivity(intent);
                finish();
                break;
            case R.id.action_remove_recipe:
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeDetailActivity.this);
                builder.setMessage("Na pewno chcesz usunąć przepis?")
                        .setTitle("Usuń przepis")
                        .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                recipeRepository.deleteAll(recipe);
                                finish();
                            }
                        })
                        .setNegativeButton("Cofnij", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_share:
                File file = new RecipePDF().writeRecipeToPDF(recipe, getApplicationContext());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivity(Intent.createChooser(share, "Share document"));
                break;
            case R.id.action_download_recipe:
                new RecipePDF().writeRecipeToPDF(recipe, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Przepis został zapisany w folderze Moje pliki/Download.", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_generate_shoppinglist:
                shoppinglistRepository = new ShoppinglistRepository(getApplicationContext());
                shoppinglistIngredientRepository = new ShoppinglistIngredientRepository(getApplicationContext());
                recipeIngredientRepository = new RecipeIngredientRepository(getApplicationContext());
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setName(recipe.getTitle());
                long shoppinglist_id = shoppinglistRepository.insertAll(shoppingList)[0];
                shoppingList = shoppinglistRepository.findById(shoppinglist_id);
                List<Recipe_Ingredient> recipe_ingredients = recipeIngredientRepository.getIngredientsForRecipe(recipe.getId());
                for(Recipe_Ingredient recipe_ingredient: recipe_ingredients){
                    ShoppingList_Ingredient shoppingList_ingredient = new ShoppingList_Ingredient();
                    shoppingList_ingredient.setName(recipe_ingredient.getName());
                    shoppingList_ingredient.setToBuy(true);
                    shoppingList_ingredient.setShoppinglist_id(shoppinglist_id);
                    shoppinglistIngredientRepository.insertAll(shoppingList_ingredient);
                }
                intent = new Intent(this, ShoppinglistDetailActivity.class);
                intent.putExtra(ShoppinglistDetailActivity.EXTRA_SHOPPINGLIST, shoppingList);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
