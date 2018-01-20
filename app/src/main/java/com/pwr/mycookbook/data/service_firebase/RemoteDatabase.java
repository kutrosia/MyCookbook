package com.pwr.mycookbook.data.service_firebase;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.model_db.Trash;
import com.pwr.mycookbook.data.model_firebase.CategoryFb;
import com.pwr.mycookbook.data.model_firebase.RecipeFb;
import com.pwr.mycookbook.data.model_firebase.RecipeIngredientFb;
import com.pwr.mycookbook.data.model_firebase.ShoppinglistFb;
import com.pwr.mycookbook.data.model_firebase.ShoppinglistIngredientFb;
import com.pwr.mycookbook.data.service_db.AppDatabase;

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
    private DatabaseReference recipe_ingredientsEndPoint;
    private DatabaseReference shoppinglistsEndPoint;
    private DatabaseReference shoppinglist_ingredientsEndPoint;
    private DatabaseReference trashEndPoint;

    private AppDatabase db;


    public RemoteDatabase(DatabaseReference userEndPoint, Context context) {
        this.userEndPoint = userEndPoint;
        this.db = AppDatabase.getAppDatabase(context);

        sync_dateEndPoint = userEndPoint.child("sync_date");
        recipesEndPoint = userEndPoint.child("recipes");
        categoriesEndPoint = userEndPoint.child("categories");
        recipe_ingredientsEndPoint = userEndPoint.child("recipe_ingredients");
        shoppinglistsEndPoint = userEndPoint.child("shoppinglist");
        shoppinglist_ingredientsEndPoint = userEndPoint.child("shoppinglist_ingredients");
        trashEndPoint = userEndPoint.child("trash");

    }

    public void writeNewRecipe(Recipe recipe){
        String key = recipesEndPoint.push().getKey();
        recipe.setKey(key);
        RecipeFb recipeFirebase = new RecipeFb();
        recipeFirebase.map(recipe);
        recipesEndPoint.child(key).setValue(recipeFirebase);
        db.recipeDao().updateAll(recipe);
    }


    public void writeNewCategory(Category category){
        String key = categoriesEndPoint.push().getKey();
        category.setKey(key);
        CategoryFb categoryFirebase = new CategoryFb();
        categoryFirebase.map(category);
        categoriesEndPoint.child(key).setValue(categoryFirebase);
        db.categoryDao().update(category);
    }

    public void writeNewRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredientsEndPoint.push().getKey();
        recipe_ingredient.setKey(key);
        RecipeIngredientFb recipe_ingredientFirebase = new RecipeIngredientFb();
        recipe_ingredientFirebase.map(recipe_ingredient);
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredientFirebase);
        db.recipe_ingredientDao().update(recipe_ingredient);
    }

    public void writeNewShoppinglist(ShoppingList shoppingList){
        String key = shoppinglistsEndPoint.push().getKey();
        shoppingList.setKey(key);
        ShoppinglistFb shoppingListFirebase = new ShoppinglistFb();
        shoppingListFirebase.map(shoppingList);
        shoppinglistsEndPoint.child(key).setValue(shoppingListFirebase);
        db.shoppingListDao().update(shoppingList);
    }

    public void writeNewShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList_ingredient){
        String key = shoppinglist_ingredientsEndPoint.push().getKey();
        shoppingList_ingredient.setKey(key);
        ShoppinglistIngredientFb shoppingList_ingredientFirebase = new ShoppinglistIngredientFb();
        shoppingList_ingredientFirebase.map(shoppingList_ingredient);
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList_ingredientFirebase);
        db.shoppingList_ingredientDao().update(shoppingList_ingredient);
    }


    public void updateRecipe(Recipe recipe){
        String key = recipe.getKey();
        RecipeFb recipeFirebase = new RecipeFb();
        recipeFirebase.map(recipe);
        recipesEndPoint.child(key).setValue(recipeFirebase);
    }


    public void updateCategory(Category category){
        String key = category.getKey();
        CategoryFb categoryFirebase = new CategoryFb();
        categoryFirebase.map(category);
        categoriesEndPoint.child(key).setValue(categoryFirebase);
    }


    public void updateRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        String key = recipe_ingredient.getKey();
        RecipeIngredientFb recipe_ingredientFirebase = new RecipeIngredientFb();
        recipe_ingredientFirebase.map(recipe_ingredient);
        recipe_ingredientsEndPoint.child(key).setValue(recipe_ingredientFirebase);
    }

    public void updateShoppinglist(ShoppingList shoppingList){
        String key = shoppingList.getKey();
        ShoppinglistFb shoppingListFirebase = new ShoppinglistFb();
        shoppingListFirebase.map(shoppingList);
        shoppinglistsEndPoint.child(key).setValue(shoppingListFirebase);
    }

    public void updateShoppinglist_Ingredient(ShoppingList_Ingredient shoppingList_ingredient){
        String key = shoppingList_ingredient.getKey();
        ShoppinglistIngredientFb shoppingList_ingredientFirebase = new ShoppinglistIngredientFb();
        shoppingList_ingredientFirebase.map(shoppingList_ingredient);
        shoppinglist_ingredientsEndPoint.child(key).setValue(shoppingList_ingredientFirebase);
    }

    public void deleteRecipe(RecipeFb recipe){
        String key = recipe.getKey();
        recipesEndPoint.child(key).removeValue();
    }


    public void deleteCategory(CategoryFb category){
        String key = category.getKey();
        categoriesEndPoint.child(key).removeValue();
    }

    public void deleteRecipe_Ingredient(RecipeIngredientFb recipe_ingredient){
        String key = recipe_ingredient.getKey();
        recipe_ingredientsEndPoint.child(key).removeValue();
    }

    public void deleteShoppinglist(ShoppinglistFb shoppingList){
        String key = shoppingList.getKey();
        shoppinglistsEndPoint.child(key).removeValue();
    }

    public void deleteShoppinglist_Ingredient(ShoppinglistIngredientFb shoppingList){
        String key = shoppingList.getKey();
        shoppinglist_ingredientsEndPoint.child(key).removeValue();
    }

    public Task<RecipeFb> getRecipeByKey(String key){
        final TaskCompletionSource<RecipeFb> tcs = new TaskCompletionSource();
        recipesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(RecipeFb.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<RecipeFb>> getRecipesList(){
        final TaskCompletionSource<List<RecipeFb>> tcs = new TaskCompletionSource();
        recipesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RecipeFb> recipes = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    recipes.add(snapshot.getValue(RecipeFb.class));
                tcs.setResult(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<RecipeFb>> getModifiedRecipesList(long date){
        final TaskCompletionSource<List<RecipeFb>> tcs = new TaskCompletionSource();
        recipesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RecipeFb> recipes = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        recipes.add(snapshot.getValue(RecipeFb.class));
                tcs.setResult(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<CategoryFb> getCategoryByKey(String key){
        final TaskCompletionSource<CategoryFb> tcs = new TaskCompletionSource();
        categoriesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(CategoryFb.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<CategoryFb>> getCategoryList(){
        final TaskCompletionSource<List<CategoryFb>> tcs = new TaskCompletionSource();
        categoriesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CategoryFb> categories = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    categories.add(snapshot.getValue(CategoryFb.class));
                tcs.setResult(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<CategoryFb>> getModifiedCategoryList(long date){
        final TaskCompletionSource<List<CategoryFb>> tcs = new TaskCompletionSource();
        categoriesEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CategoryFb> categories = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        categories.add(snapshot.getValue(CategoryFb.class));
                tcs.setResult(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<RecipeIngredientFb> getRecipeIngredientByKey(String key){
        final TaskCompletionSource<RecipeIngredientFb> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(RecipeIngredientFb.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<RecipeIngredientFb>> getRecipeIngredientList(){
        final TaskCompletionSource<List<RecipeIngredientFb>> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RecipeIngredientFb> recipe_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    recipe_ingredients.add(snapshot.getValue(RecipeIngredientFb.class));
                tcs.setResult(recipe_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<RecipeIngredientFb>> getIngredientsForRecipe(String recipe_key){
        final TaskCompletionSource<List<RecipeIngredientFb>> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RecipeIngredientFb> recipe_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    RecipeIngredientFb recipeIngredient = snapshot.getValue(RecipeIngredientFb.class);
                    if(recipeIngredient.getRecipe_key().equals(recipe_key))
                        recipe_ingredients.add(recipeIngredient);
                }
                tcs.setResult(recipe_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<RecipeIngredientFb>> getModifiedRecipeIngredientList(long date){
        final TaskCompletionSource<List<RecipeIngredientFb>> tcs = new TaskCompletionSource();
        recipe_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RecipeIngredientFb> recipe_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        recipe_ingredients.add(snapshot.getValue(RecipeIngredientFb.class));
                tcs.setResult(recipe_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<ShoppinglistFb> getShoppinglistByKey(String key){
        final TaskCompletionSource<ShoppinglistFb> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(ShoppinglistFb.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<ShoppinglistFb>> getShoppinglistList(){
        final TaskCompletionSource<List<ShoppinglistFb>> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppinglistFb> shoppingLists = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    shoppingLists.add(snapshot.getValue(ShoppinglistFb.class));
                tcs.setResult(shoppingLists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<ShoppinglistFb>> getModifiedShoppinglistList(long date){
        final TaskCompletionSource<List<ShoppinglistFb>> tcs = new TaskCompletionSource();
        shoppinglistsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppinglistFb> shoppingLists = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        shoppingLists.add(snapshot.getValue(ShoppinglistFb.class));
                tcs.setResult(shoppingLists);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<ShoppinglistIngredientFb> getShoppinglistIngredientByKey(String key){
        final TaskCompletionSource<ShoppinglistIngredientFb> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tcs.setResult(dataSnapshot.getValue(ShoppinglistIngredientFb.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<ShoppinglistIngredientFb>> getShoppinglistIngredientList(){
        final TaskCompletionSource<List<ShoppinglistIngredientFb>> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppinglistIngredientFb> shoppingList_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    shoppingList_ingredients.add(snapshot.getValue(ShoppinglistIngredientFb.class));
                tcs.setResult(shoppingList_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<ShoppinglistIngredientFb>> getIngredientsForShoppinglist(String shoppinglist_key){
        final TaskCompletionSource<List<ShoppinglistIngredientFb>> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppinglistIngredientFb> shoppingList_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ShoppinglistIngredientFb shoppinglistIngredientFb = snapshot.getValue(ShoppinglistIngredientFb.class);
                    if(shoppinglistIngredientFb.getShoppinglist_key().equals(shoppinglist_key)){
                        shoppingList_ingredients.add(shoppinglistIngredientFb);
                    }
                }
                tcs.setResult(shoppingList_ingredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setException(databaseError.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<ShoppinglistIngredientFb>> getModifiedShoppinglistIngredientList(long date){
        final TaskCompletionSource<List<ShoppinglistIngredientFb>> tcs = new TaskCompletionSource();
        shoppinglist_ingredientsEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppinglistIngredientFb> shoppingList_ingredients = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    if(snapshot.child("modification_date").getValue(Long.class) < date)
                        shoppingList_ingredients.add(snapshot.getValue(ShoppinglistIngredientFb.class));
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

    public Task<List<Trash>> getTrashList(long date){
        final TaskCompletionSource<List<Trash>> tcs = new TaskCompletionSource<>();
        trashEndPoint.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Trash> trashList = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.child("date").getValue(Long.class) > date){
                        trashList.add(snapshot.getValue(Trash.class));
                    }
                }
                tcs.setResult(trashList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tcs.getTask();
    }

    public void setTrashList(List<Trash> trashList){
        for(Trash trash: trashList){
            trashEndPoint.setValue(trash);
        }
    }
}
