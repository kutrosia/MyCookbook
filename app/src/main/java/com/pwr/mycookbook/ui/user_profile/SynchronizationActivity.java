package com.pwr.mycookbook.ui.user_profile;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model.SyncDate;
import com.pwr.mycookbook.data.service.AppDatabase;
import com.pwr.mycookbook.data.model.Category;
import com.pwr.mycookbook.data.model.Ingredient;
import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;
import com.pwr.mycookbook.data.model.ShoppingList;
import com.pwr.mycookbook.data.model.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service.RemoteDatabase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 30.12.2017.
 */

public class SynchronizationActivity extends AppCompatActivity {


    private Button sync_button;
    private AppDatabase db;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private RemoteDatabase remoteDatabase;

    private long firebaseSyncDate;
    private long databaseSyncDate;
    private SyncDate syncDate;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        sync_button = findViewById(R.id.sync_button);
        sync_button.setOnClickListener(onSyncButtonClick());

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if(user == null){
            sync_button.setClickable(false);
            sync_button.setActivated(false);
            sync_button.setEnabled(false);
        }
        else{
            sync_button.setEnabled(true);
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference userEndPoint = firebaseDatabase.getReference(user.getUid());
            remoteDatabase = new RemoteDatabase(userEndPoint, db);
        }
    }

    private View.OnClickListener onSyncButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSynchronization();
            }
        };
    }

    private void performSynchronization() {
        sync_button.setEnabled(false);
        syncDate = db.syncDateDao().getAll();
        if(syncDate == null){
            Calendar rightNow = Calendar.getInstance();
            long currentTime = rightNow.getTimeInMillis();
            syncDate = new SyncDate(currentTime);
            db.syncDateDao().insertAll(syncDate);
        }
        databaseSyncDate = syncDate.getDate();

        remoteDatabase.getDatabaseSyncDate().addOnCompleteListener(new OnCompleteListener<Long>() {
            @Override
            public void onComplete(@NonNull Task<Long> task) {
                firebaseSyncDate = task.getResult();
                if(firebaseSyncDate > databaseSyncDate){
                    Toast.makeText(getApplicationContext(), "Synchronizacja lokalnej bazy danych", Toast.LENGTH_LONG).show();
                    updateDatabase();
                }else if(firebaseSyncDate < databaseSyncDate){
                    Toast.makeText(getApplicationContext(), "Synchronizacja zdalnej bazy danych", Toast.LENGTH_LONG).show();
                    updateFirebase();
                }else if(firebaseSyncDate == databaseSyncDate){
                    Toast.makeText(getApplicationContext(), "Bazy danych są zsynchronizowane", Toast.LENGTH_LONG).show();
                    sync_button.setEnabled(false);
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void updateDatabase() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                remoteDatabase.getModifiedRecipesList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<Recipe>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Recipe>> task) {
                        List<Recipe> recipes = task.getResult();
                        for(Recipe recipe: recipes) {
                            try{
                                db.recipeDao().updateAll(recipe);
                            }catch(SQLiteException e){
                                db.recipeDao().insertAll(recipe);
                            }
                        }
                    }
                });


                remoteDatabase.getModifiedCategoryList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<Category>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Category>> task) {
                        List<Category> categories = task.getResult();
                        for(Category category: categories) {
                            try{
                                db.categoryDao().update(category);
                            }catch(SQLiteException e){
                                db.categoryDao().insertAll(category);
                            }
                        }
                    }
                });


                remoteDatabase.getModifiedIngredientList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<Ingredient>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Ingredient>> task) {
                        List<Ingredient> ingredients = task.getResult();
                        for(Ingredient ingredient: ingredients) {
                            try{
                                db.ingredientDao().update(ingredient);
                            }catch(SQLiteException e){
                                db.ingredientDao().insertAll(ingredient);
                            }
                        }
                    }
                });


                remoteDatabase.getModifiedRecipeIngredientList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<Recipe_Ingredient>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Recipe_Ingredient>> task) {
                        List<Recipe_Ingredient> recipe_ingredients = task.getResult();
                        for(Recipe_Ingredient recipe_ingredient: recipe_ingredients) {
                            try{
                                db.recipe_ingredientDao().update(recipe_ingredient);
                            }catch(SQLiteException e){
                                db.recipe_ingredientDao().insertAll(recipe_ingredient);
                            }
                        }
                    }
                });


                remoteDatabase.getModifiedShoppinglistList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<ShoppingList>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<ShoppingList>> task) {
                        List<ShoppingList> shoppingLists = task.getResult();
                        for(ShoppingList shoppingList: shoppingLists) {
                            try{
                                db.shoppingListDao().update(shoppingList);
                            }catch(SQLiteException e){
                                db.shoppingListDao().insertAll(shoppingList);
                            }
                        }
                    }
                });


                remoteDatabase.getModifiedShoppinglistIngredientList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<ShoppingList_Ingredient>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<ShoppingList_Ingredient>> task) {
                        List<ShoppingList_Ingredient> shoppingList_ingredients = task.getResult();
                        for(ShoppingList_Ingredient shoppingList_ingredient: shoppingList_ingredients) {
                            try{
                                db.shoppingList_ingredientDao().update(shoppingList_ingredient);
                            }catch(SQLiteException e){
                                db.shoppingList_ingredientDao().insertAll(shoppingList_ingredient);
                            }
                        }
                    }
                });

                syncDate.setDate(firebaseSyncDate);
                db.syncDateDao().update(syncDate);
                return "Synchronizacja zakończona pomyślnie";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateFirebase(){

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                String message;
                List<Recipe> recipes = db.recipeDao().getNotModified(firebaseSyncDate);
                message = String.valueOf(recipes.size());
                //Toast.makeText(getApplicationContext(), String.valueOf(recipes.size()), Toast.LENGTH_LONG).show();
                for(Recipe recipe: recipes){
                    if(recipe.getKey() != null){
                        //Toast.makeText(getApplicationContext(), "Aktualizowanie przepisu...", Toast.LENGTH_SHORT).show();
                        message += recipe.getKey()+  " aktualizowanie przepisu";
                        remoteDatabase.updateRecipe(recipe);
                    }
                    else{
                        //Toast.makeText(getApplicationContext(), "Dodawanie przepisu...", Toast.LENGTH_SHORT).show();
                        message += " dodawanie przepisu";
                        remoteDatabase.writeNewRecipe(recipe);
                    }
                }

                List<Category> categories = db.categoryDao().getNotModified(firebaseSyncDate);
                for(Category category: categories){
                    if(category.getKey() != null)
                        remoteDatabase.updateCategory(category);
                    else
                        remoteDatabase.writeNewCategory(category);
                }

                List<Ingredient> ingredients = db.ingredientDao().getNotModified(firebaseSyncDate);
                for(Ingredient ingredient: ingredients){
                    if(ingredient.getKey() != null)
                        remoteDatabase.updateIngredient(ingredient);
                    else
                        remoteDatabase.writeNewIngredient(ingredient);
                }

                List<Recipe_Ingredient> recipe_ingredients = db.recipe_ingredientDao().getNotModified(firebaseSyncDate);
                for(Recipe_Ingredient recipe_ingredient: recipe_ingredients){
                    if(recipe_ingredient.getKey() != null)
                        remoteDatabase.updateRecipe_Ingredient(recipe_ingredient);
                    else
                        remoteDatabase.writeNewRecipe_Ingredient(recipe_ingredient);
                }

                List<ShoppingList> shoppingLists = db.shoppingListDao().getNotModified(firebaseSyncDate);
                for(ShoppingList shoppingList: shoppingLists){
                    if(shoppingList.getKey() != null)
                        remoteDatabase.updateShoppinglist(shoppingList);
                    else
                        remoteDatabase.writeNewShoppinglist(shoppingList);
                }

                List<ShoppingList_Ingredient> shoppingList_ingredients = db.shoppingList_ingredientDao().getNotModified(firebaseSyncDate);
                for(ShoppingList_Ingredient shoppingList_ingredient: shoppingList_ingredients){
                    if(shoppingList_ingredient.getKey() != null)
                        remoteDatabase.updateShoppinglist_Ingredient(shoppingList_ingredient);
                    else
                        remoteDatabase.writeNewShoppinglist_Ingredient(shoppingList_ingredient);
                }

                remoteDatabase.setDatabaseSyncDate(databaseSyncDate);
                return message;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), "Proces synchronizacji zakończony: " + s, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }



}
