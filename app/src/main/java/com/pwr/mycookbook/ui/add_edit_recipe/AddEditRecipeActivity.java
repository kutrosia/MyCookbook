package com.pwr.mycookbook.ui.add_edit_recipe;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.pwr.mycookbook.data.file.BitmapSave;
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
import com.pwr.mycookbook.ui.settings.SettingsActivity;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.List;

public class AddEditRecipeActivity extends AppCompatActivity implements IPickResult, IRecipeChange {
    public static final String EXTRA_RECIPE = "recipe";
    private Recipe recipe;
    private List<Recipe_Ingredient> recipe_ingredients;
    private RecipeRepository recipeRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private AddEditRecipeFragment addEditRecipeFragment;
    private AddEditRecipeIngredientsFragment addEditRecipeIngredientsFragment;
    private AddEditRecipePreparationFragment addEditRecipePreparationFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_add_edit_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipeRepository = new RecipeRepository(getApplicationContext());
        recipeIngredientRepository = new RecipeIngredientRepository(getApplicationContext());
        recipe = (Recipe) getIntent().getExtras().get(EXTRA_RECIPE);

        ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        addEditRecipeFragment = AddEditRecipeFragment.newInstance(recipe);
        addEditRecipeIngredientsFragment = AddEditRecipeIngredientsFragment.newInstance(recipe);
        addEditRecipePreparationFragment = AddEditRecipePreparationFragment.newInstance(recipe);

        pagerAdapter.addFragment(addEditRecipeFragment, "Szczegóły");
        pagerAdapter.addFragment(addEditRecipeIngredientsFragment, "Składniki");
        pagerAdapter.addFragment(addEditRecipePreparationFragment, "Instrukcje");

        viewPager.setAdapter(pagerAdapter);


        TabLayout tabLayout = findViewById(R.id.tabs);
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
        getMenuInflater().inflate(R.menu.recipe_add_edit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add_photo:
                showImageDialog();
                break;
            case R.id.action_save:
                addEditRecipeFragment.saveRecipe();
                addEditRecipePreparationFragment.saveRecipe();
                addEditRecipeIngredientsFragment.saveRecipe();
                if(recipe.getTitle().equals("")){
                    Toast.makeText(getApplicationContext(), "Przepis musi mieć tytuł", Toast.LENGTH_LONG).show();
                }else{
                    insertOrUpdateRecipeToDb();
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void insertOrUpdateRecipeToDb(){
        long recipe_id;
        if(recipe.isNew() || recipe.isImported()){
            recipe_id = recipeRepository.insertAll(recipe)[0];
        }else{
            recipeRepository.updateAll(recipe);
            recipe_id = recipe.getId();
        }

        for(Recipe_Ingredient recipe_ingredient: recipe_ingredients){
            if(recipe_ingredient.isNew()){
                recipe_ingredient.setRecipe_id(recipe_id);
                recipeIngredientRepository.insertAll(recipe_ingredient);
            }else{
                recipeIngredientRepository.update(recipe_ingredient);
            }
        }
    }


    private void showImageDialog() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(AddEditRecipeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(AddEditRecipeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }else{
            ActivityCompat.requestPermissions(AddEditRecipeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PackageManager pm = getPackageManager();

                    if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                        PickImageDialog.build(new PickSetup()).show(AddEditRecipeActivity.this);

                    } else {

                        Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            Bitmap bitmap = pickResult.getBitmap();

            BitmapSave bs = new BitmapSave();
            bs.saveBitmap(bitmap, getApplicationContext());
            String path = bs.getFilePath();

            recipe.setPhoto(path);
            addEditRecipeFragment.setPhoto(bitmap);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recipe.getTitle() == null){
            recipeRepository.deleteAll(recipe);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(recipe.getTitle() == null){
            recipeRepository.deleteAll(recipe);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recipeRepository.deleteAll(recipe);

    }

    @Override
    public boolean onNavigateUp() {
        recipeRepository.deleteAll(recipe);
        return super.onNavigateUp();
    }

    @Override
    public void setRecipeDetail(Recipe recipe) {
        this.recipe.setTitle(recipe.getTitle());
        this.recipe.setCategory_id(recipe.getCategory_id());
        this.recipe.setPhoto(recipe.getPhoto());
        this.recipe.setTime(recipe.getTime());
        this.recipe.setPortion(recipe.getPortion());
    }

    @Override
    public void setRecipeDescription(Recipe recipe) {
        this.recipe.setDescription(recipe.getDescription());
        this.recipe.setNote(recipe.getNote());
    }

    @Override
    public void setRecipeIngredients(List<Recipe_Ingredient> recipeIngredients) {
        this.recipe_ingredients = recipeIngredients;
    }
}
