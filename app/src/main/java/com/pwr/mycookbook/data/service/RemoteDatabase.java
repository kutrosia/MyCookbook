package com.pwr.mycookbook.data.service;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pwr.mycookbook.data.model.Category;
import com.pwr.mycookbook.data.model.Ingredient;
import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;
import com.pwr.mycookbook.data.model.ShoppingList;
import com.pwr.mycookbook.data.model.ShoppingList_Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaku on 01.01.2018.
 */

public class RemoteDatabase {

    private DatabaseReference userEndPoint;
    private DatabaseReference sync_dateEndPoint;
    private DatabaseReference recipesEndPoint;
    private DatabaseReference categoriesEndPoint;
    private DatabaseReference ingredientsEndPoint;
    private DatabaseReference recipe_ingredientsEndPoint;
    private DatabaseReference shoppinglistsEndPoint;
    private DatabaseReference shoppinglist_ingredientsEndPoint;

    private AppDatabase db;


    public RemoteDatabase(DatabaseReference userEndPoint, AppDatabase db) {
        this.userEndPoint = userEndPoint;
        this.db = db;

        sync_dateEndPoint = userEndPoint.child("sync_date");
        recipesEndPoint = userEndPoint.child("recipes");
        categoriesEndPoint = userEndPoint.child("categories");
        ingredientsEndPoint = userEndPoint.child("ingredients");
        recipe_ingredientsEndPoint = userEndPoint.child("recipe_ingredients");
        shoppinglistsEndPoint = userEndPoint.child("shoppinglist");
        shoppinglist_ingredientsEndPoint = userEndPoint.child("shoppinglist_ingredients");

    }

    public void writeNewRecipe(Recipe recipe){
        String key = recipesEndPoint.push().getKey();
        recipe.setKey(key);
        recipesEndPoint.child(key).setValue(recipe);
        db.recipeDao().updateAll(recipe);
    }


    public void writeNewCategory(Category category){
        String key = categoriesEndPoint.push().getKey();
        category.setKey(key);
        categoriesEndPoint.child(key).setValue(category);
        db.categoryDao().update(category);
    }


    public void writeNewIngredient(Ingredient ingredient){
        String key = ingredientsEndPoint.push().getKey();
        ingredient.setKey(key);
        ingredientsEndPoint.child(key).setValue(ingredient);
        db.ingredientDao().update(ingredient);
    }

    public void writeNewRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredientsEndPoint.push().getKey();
        recipe_ingredient.setKey(key);
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredient);
        db.recipe_ingredientDao().update(recipe_ingredient);
    }

    public void writeNewShoppinglist(ShoppingList shoppingList){
        String key = shoppinglistsEndPoint.push().getKey();
        shoppingList.setKey(key);
        shoppinglistsEndPoint.child(key).setValue(shoppingList);
        db.shoppingListDao().update(shoppingList);
    }

    public void writeNewShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppinglist_ingredientsEndPoint.push().getKey();
        shoppingList.setKey(key);
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList);
        db.shoppingList_ingredientDao().update(shoppingList);
    }


    public void updateRecipe(Recipe recipe){
        String key = recipe.getKey();
        recipesEndPoint.child(key).setValue(recipe);
    }


    public void updateCategory(Category category){
        String key = category.getKey();
        categoriesEndPoint.child(key).setValue(category);
    }


    public void updateIngredient(Ingredient ingredient){
        String key = ingredient.getKey();
        ingredientsEndPoint.child(key).setValue(ingredient);
    }

    public void updateRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredient.getKey();
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredient);
    }

    public void updateShoppinglist(ShoppingList shoppingList){
        String key = shoppingList.getKey();
        shoppinglistsEndPoint.child(key).setValue(shoppingList);
    }

    public void updateShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppingList.getKey();
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList);
    }

    public void deleteRecipe(Recipe recipe){
        String key = recipe.getKey();
        recipesEndPoint.child(key).removeValue();
    }


    public void deleteCategory(Category category){
        String key = category.getKey();
        categoriesEndPoint.child(key).removeValue();
    }


    public void deleteIngredient(Ingredient ingredient){
        String key = ingredient.getKey();
        ingredientsEndPoint.child(key).removeValue();
    }

    public void deleteRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredient.getKey();
        recipe_ingredientsEndPoint.child(key).removeValue();
    }

    public void deleteShoppinglist(ShoppingList shoppingList){
        String key = shoppingList.getKey();
        shoppinglistsEndPoint.child(key).removeValue();
    }

    public void deleteShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList){
        String key = shoppingList.getKey();
        shoppinglist_ingredientsEndPoint.child(key).removeValue();
    }

    public Task<Recipe> getRecipeByKey(String key){
        final TaskCompletionSource<Recipe> tcs = new TaskCompletionSource();
        recipesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(Recipe.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<Recipe>> getRecipesList(){
        final TaskCompletionSource<List<Recipe>> tcs = new TaskCompletionSource();
        recipesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    recipes.add(snapshot.getValue(Recipe.class));
                tcs.setResult(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Recipe>> getModifiedRecipesList(long date){
        final TaskCompletionSource<List<Recipe>> tcs = new TaskCompletionSource();
        recipesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        recipes.add(snapshot.getValue(Recipe.class));
                tcs.setResult(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Category> getCategoryByKey(String key){
        final TaskCompletionSource<Category> tcs = new TaskCompletionSource();
        categoriesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(Category.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<Category>> getCategoryList(){
        final TaskCompletionSource<List<Category>> tcs = new TaskCompletionSource();
        categoriesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    categories.add(snapshot.getValue(Category.class));
                tcs.setResult(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Category>> getModifiedCategoryList(long date){
        final TaskCompletionSource<List<Category>> tcs = new TaskCompletionSource();
        categoriesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        categories.add(snapshot.getValue(Category.class));
                tcs.setResult(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Ingredient> getIngredientByKey(String key){
        final TaskCompletionSource<Ingredient> tcs = new TaskCompletionSource();
        ingredientsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(Ingredient.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<Ingredient>> getIngredientList(){
        final TaskCompletionSource<List<Ingredient>> tcs = new TaskCompletionSource();
        ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ingredient> ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    ingredients.add(snapshot.getValue(Ingredient.class));
                tcs.setResult(ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Ingredient>> getModifiedIngredientList(long date){
        final TaskCompletionSource<List<Ingredient>> tcs = new TaskCompletionSource();
        ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ingredient> ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                    ingredients.add(snapshot.getValue(Ingredient.class));
                tcs.setResult(ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Recipe_Ingredient> getRecipeIngredientByKey(String key){
        final TaskCompletionSource<Recipe_Ingredient> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(Recipe_Ingredient.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<Recipe_Ingredient>> getRecipeIngredientList(){
        final TaskCompletionSource<List<Recipe_Ingredient>> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe_Ingredient> recipe_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    recipe_ingredients.add(snapshot.getValue(Recipe_Ingredient.class));
                tcs.setResult(recipe_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Recipe_Ingredient>> getModifiedRecipeIngredientList(long date){
        final TaskCompletionSource<List<Recipe_Ingredient>> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe_Ingredient> recipe_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        recipe_ingredients.add(snapshot.getValue(Recipe_Ingredient.class));
                tcs.setResult(recipe_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<ShoppingList> getShoppinglistByKey(String key){
        final TaskCompletionSource<ShoppingList> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(ShoppingList.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<ShoppingList>> getShoppinglistList(){
        final TaskCompletionSource<List<ShoppingList>> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppingList> shoppingLists = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    shoppingLists.add(snapshot.getValue(ShoppingList.class));
                tcs.setResult(shoppingLists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<ShoppingList>> getModifiedShoppinglistList(long date){
        final TaskCompletionSource<List<ShoppingList>> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppingList> shoppingLists = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        shoppingLists.add(snapshot.getValue(ShoppingList.class));
                tcs.setResult(shoppingLists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<ShoppingList_Ingredient> getShoppinglistIngredientByKey(String key){
        final TaskCompletionSource<ShoppingList_Ingredient> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(ShoppingList_Ingredient.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<ShoppingList_Ingredient>> getShoppinglistIngredientList(){
        final TaskCompletionSource<List<ShoppingList_Ingredient>> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppingList_Ingredient> shoppingList_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    shoppingList_ingredients.add(snapshot.getValue(ShoppingList_Ingredient.class));
                tcs.setResult(shoppingList_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<ShoppingList_Ingredient>> getModifiedShoppinglistIngredientList(long date){
        final TaskCompletionSource<List<ShoppingList_Ingredient>> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppingList_Ingredient> shoppingList_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        shoppingList_ingredients.add(snapshot.getValue(ShoppingList_Ingredient.class));
                tcs.setResult(shoppingList_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getRecipesCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        recipesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getCategoriesCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        categoriesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getIngredientsCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getRecipeIngredientsCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getShoppinglistsCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<Long> getShoppinglistIngredientsCount(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }




    public void setDatabaseSyncDate(long date){
        sync_dateEndPoint.setValue(date);
    }

    public Task<Long> getDatabaseSyncDate(){
        final TaskCompletionSource<Long> tcs = new TaskCompletionSource();
        sync_dateEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(Long.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }
}
