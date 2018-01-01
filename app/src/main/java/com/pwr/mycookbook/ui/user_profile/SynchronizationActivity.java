package com.pwr.mycookbook.ui.user_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.data.model.Category;
import com.pwr.mycookbook.data.model.Ingredient;
import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;
import com.pwr.mycookbook.data.model.ShoppingList;
import com.pwr.mycookbook.data.model.ShoppingList_Ingredient;

import java.util.List;

/**
 * Created by olaku on 30.12.2017.
 */

public class SynchronizationActivity extends AppCompatActivity {


    private Button sync_button;
    private AppDatabase db;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference userEndPoint;
    private DatabaseReference sync_dateEndPoint;
    private DatabaseReference recipesEndPoint;
    private DatabaseReference categoriesEndPoint;
    private DatabaseReference ingredientsEndPoint;
    private DatabaseReference recipe_ingredientsEndPoint;
    private DatabaseReference shoppinglistsEndPoint;
    private DatabaseReference shoppinglist_ingredientsEndPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        db = AppDatabase.getAppDatabase(getApplicationContext());
        sync_button = findViewById(R.id.sync_button);
        sync_button.setOnClickListener(onSyncButtonClick());
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
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            userEndPoint = firebaseDatabase.getReference(user.getUid());

            sync_dateEndPoint = userEndPoint.child("sync_date");
            recipesEndPoint = userEndPoint.child("recipes");
            categoriesEndPoint = userEndPoint.child("categories");
            ingredientsEndPoint = userEndPoint.child("ingredients");
            recipe_ingredientsEndPoint = userEndPoint.child("recipe_ingredients");
            shoppinglistsEndPoint = userEndPoint.child("shoppinglist");
            shoppinglist_ingredientsEndPoint = userEndPoint.child("shoppinglist_ingredients");

            updateFirebase();
        }
    }

    private void updateFirebase(){


        List<Recipe> recipes = db.recipeDao().getAll();
        for(Recipe recipe: recipes){
            if(recipe.getKey() != null)
                updateRecipe(recipe);
            else
                writeNewRecipe(recipe);
        }

        List<Category> categories = db.categoryDao().getAll();
        for(Category category: categories){
            if(category.getKey() != null)
                updateCategory(category);
            else
                writeNewCategory(category);
        }

        List<Ingredient> ingredients = db.ingredientDao().getAll();
        for(Ingredient ingredient: ingredients){
            if(ingredient.getKey() != null)
                updateIngredient(ingredient);
            else
                writeNewIngredient(ingredient);
        }

        List<Recipe_Ingredient> recipe_ingredients = db.recipe_ingredientDao().getAll();
        for(Recipe_Ingredient recipe_ingredient: recipe_ingredients){
            if(recipe_ingredient.getKey() != null)
                updateRecipe_Ingredient(recipe_ingredient);
            else
                writeNewRecipe_Ingredient(recipe_ingredient);
        }

        List<ShoppingList> shoppingLists = db.shoppingListDao().getAll();
        for(ShoppingList shoppingList: shoppingLists){
            if(shoppingList.getKey() != null)
                updateShoppinglist(shoppingList);
            else
                writeNewShoppinglist(shoppingList);
        }

        List<ShoppingList_Ingredient> shoppingList_ingredients = db.shoppingList_ingredientDao().getAll();
        for(ShoppingList_Ingredient shoppingList_ingredient: shoppingList_ingredients){
            if(shoppingList_ingredient.getKey() != null)
                updateShoppinglist_Ingredient(shoppingList_ingredient);
            else
                writeNewShoppinglist_Ingredient(shoppingList_ingredient);
        }

        sync_dateEndPoint.setValue(System.currentTimeMillis());
    }

    private void writeNewRecipe(Recipe recipe){
        String key = recipesEndPoint.push().getKey();
        recipe.setKey(key);
        recipesEndPoint.child(key).setValue(recipe);
        db.recipeDao().updateAll(recipe);
    }


    private void writeNewCategory(Category category){
        String key = categoriesEndPoint.push().getKey();
        category.setKey(key);
        categoriesEndPoint.child(key).setValue(category);
        db.categoryDao().update(category);
    }


    private void writeNewIngredient(Ingredient ingredient){
        String key = ingredientsEndPoint.push().getKey();
        ingredient.setKey(key);
        ingredientsEndPoint.child(key).setValue(ingredient);
        db.ingredientDao().update(ingredient);
    }

    private void writeNewRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredientsEndPoint.push().getKey();
        recipe_ingredient.setKey(key);
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredient);
        db.recipe_ingredientDao().update(recipe_ingredient);
    }

    private void writeNewShoppinglist(ShoppingList shoppingList){
        String key = shoppinglistsEndPoint.push().getKey();
        shoppingList.setKey(key);
        shoppinglistsEndPoint.child(key).setValue(shoppingList);
        db.shoppingListDao().update(shoppingList);
    }

    private void writeNewShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppinglist_ingredientsEndPoint.push().getKey();
        shoppingList.setKey(key);
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList);
        db.shoppingList_ingredientDao().update(shoppingList);
    }


    private void updateRecipe(Recipe recipe){
        String key = recipe.getKey();
        recipesEndPoint.child(key).setValue(recipe);
    }


    private void updateCategory(Category category){
        String key = category.getKey();
        categoriesEndPoint.child(key).setValue(category);
    }


    private void updateIngredient(Ingredient ingredient){
        String key = ingredient.getKey();
        ingredientsEndPoint.child(key).setValue(ingredient);
    }

    private void updateRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredient.getKey();
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredient);
    }

    private void updateShoppinglist(ShoppingList shoppingList){
        String key = shoppingList.getKey();
        shoppinglistsEndPoint.child(key).setValue(shoppingList);
    }

    private void updateShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppingList.getKey();
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList);
    }

    private void deleteRecipe(Recipe recipe){
        String key = recipe.getKey();
        recipesEndPoint.child(key).removeValue();
    }


    private void deleteCategory(Category category){
        String key = category.getKey();
        categoriesEndPoint.child(key).removeValue();
    }


    private void deleteIngredient(Ingredient ingredient){
        String key = ingredient.getKey();
        ingredientsEndPoint.child(key).removeValue();
    }

    private void deleteRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredient.getKey();
        recipe_ingredientsEndPoint.child(key).removeValue();
    }

    private void deleteShoppinglist(ShoppingList shoppingList){
        String key = shoppingList.getKey();
        shoppinglistsEndPoint.child(key).removeValue();
    }

    private void deleteShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppingList.getKey();
        shoppinglist_ingredientsEndPoint.child(key).removeValue();
    }

    private void addValueEventListenerOnRecipes(){
        recipesEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
