package com.pwr.mycookbook.data.service_firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
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
import com.pwr.mycookbook.data.service_db.CategoryRepository;
import com.pwr.mycookbook.data.service_db.DatabaseDateRepository;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;
import com.pwr.mycookbook.data.service_db.RecipeRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;
import com.pwr.mycookbook.data.service_db.TrashRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 31.01.2018.
 */

public class DataBasesSynchronization {

    private Context context;
    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private ShoppinglistRepository shoppinglistRepository;
    private ShoppinglistIngredientRepository shoppinglistIngredientRepository;
    private DatabaseDateRepository databaseDateRepository;
    private TrashRepository trashRepository;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private RemoteDatabase remoteDatabase;

    private long firebaseSyncDate;
    private long databaseSyncDate;
    private long databaseModifiedDate;
    private DatabaseDate databaseDate;
    private long currentTime;

    private List<Trash> firebaseTrash;

    public DataBasesSynchronization(Context context) {
        this.context = context;

        Calendar calendar = Calendar.getInstance();
        currentTime = calendar.getTimeInMillis();

        recipeRepository = new RecipeRepository(context);
        recipeIngredientRepository = new RecipeIngredientRepository(context);
        categoryRepository = new CategoryRepository(context);
        shoppinglistRepository = new ShoppinglistRepository(context);
        shoppinglistIngredientRepository = new ShoppinglistIngredientRepository(context);
        trashRepository = new TrashRepository(context);
        databaseDateRepository = new DatabaseDateRepository(context);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(user != null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference userEndPoint = firebaseDatabase.getReference(user.getUid());
            remoteDatabase = new RemoteDatabase(userEndPoint, context);

            remoteDatabase.getDatabaseSyncDate().addOnCompleteListener(new OnCompleteListener<Long>() {
                @Override
                public void onComplete(@NonNull Task<Long> task) {
                    firebaseSyncDate = task.getResult();
                }
            });
        }
    }


    public boolean isNeeded(){
        if(user == null)
            return false;
        return checkForUpdates();
    }


    private boolean checkForUpdates() {
        databaseDate = databaseDateRepository.getAll();
        if(databaseDate == null){
            createDate();
        }
        databaseSyncDate = databaseDate.getSync_date();
        if(currentTime-databaseSyncDate < 180000)
            return false;
        databaseModifiedDate = databaseDate.getModified_date();
        return firebaseSyncDate != databaseModifiedDate;
    }

    private void createDate() {
        databaseDate = new DatabaseDate();
        databaseDate.setSync_date(0);
        databaseDate.setModified_date(0);
        databaseDateRepository.insertAll(databaseDate);
    }

    public void synchronize() {
        remoteDatabase.getTrashList(databaseSyncDate).addOnCompleteListener(new OnCompleteListener<List<Trash>>() {
            @Override
            public void onComplete(@NonNull Task<List<Trash>> task) {
                if(task.getResult() == null)
                    firebaseTrash = new ArrayList<>();
                else
                    firebaseTrash = task.getResult();
                syncCategory();
                syncShoppinglists();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void syncCategory(){

        new AsyncTask<Void, Void, String>() {
            //for each category in local database check if the category exist in firebase and if we have to update
            @Override
            protected String doInBackground(Void... voids) {
                List<Category> categories = categoryRepository.getAll();
                for(Category category: categories){
                    if(category.getKey()== null)
                        remoteDatabase.writeNewCategory(category);
                    else{
                        remoteDatabase.getCategoryByKey(category.getKey()).addOnCompleteListener(new OnCompleteListener<CategoryFb>() {
                            @Override
                            public void onComplete(@NonNull Task<CategoryFb> task) {
                                CategoryFb categoryFb = task.getResult();
                                if(categoryFb == null){
                                    if(isInFirebaseTrash(categoryFb.getKey(), categoryFb.getModification_date())){
                                        categoryRepository.delete(category);
                                    }else
                                        remoteDatabase.writeNewCategory(category);
                                }
                            }
                        });
                    }
                }

                //for each category in firebase check if the category exist in local database and if we have to update
                remoteDatabase.getCategoryList().addOnCompleteListener(new OnCompleteListener<List<CategoryFb>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<CategoryFb>> task) {
                        List<CategoryFb> categories = task.getResult();
                        for(CategoryFb category: categories) {
                            Category categoryInDb = categoryRepository.findByKey(category.getKey());
                            if(categoryInDb == null){
                                if(isInTrash(category.getKey(), category.getModification_date())){
                                    remoteDatabase.deleteCategory(category);
                                    Trash trashFirebase = new Trash("Category", category.getKey(), currentTime);
                                    firebaseTrash.add(trashFirebase);
                                }else{
                                    categoryInDb = new Category();
                                    categoryInDb.map(category);
                                    categoryRepository.insertAll(categoryInDb);
                                }
                            }else if(categoryInDb.getModification_date() < category.getModification_date()){
                                categoryInDb.map(category);
                                categoryRepository.update(categoryInDb);
                            }else if(categoryInDb.getModification_date() > category.getModification_date()){
                                remoteDatabase.updateCategory(categoryInDb);
                            }
                        }
                    }
                });
                return "Synchronizacja kategorii zakończona pomyślnie";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                syncRecipe();

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void syncRecipe(){
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                List<Recipe> recipes = recipeRepository.getAll();
                for(Recipe recipe: recipes){
                    if(recipe.getKey() == null){
                        Category category = categoryRepository.findById(recipe.getCategory_id());
                        if(category != null)
                            recipe.setCategory_key(category.getKey());
                        remoteDatabase.writeNewRecipe(recipe);
                        addRecipeIngredientsToFirebase(recipe.getId(), recipe.getKey());
                    }else{
                        remoteDatabase.getRecipeByKey(recipe.getKey()).addOnCompleteListener(new OnCompleteListener<RecipeFb>() {
                            @Override
                            public void onComplete(@NonNull Task<RecipeFb> task) {
                                RecipeFb recipeFb = task.getResult();
                                if(recipeFb == null){
                                    if(isInFirebaseTrash(recipe.getKey(), recipe.getModification_date())){
                                        recipeRepository.deleteAll(recipe);
                                        recipeIngredientRepository.deleteRecipeWithIngredients(recipe.getId());
                                    }else{
                                        Category category = categoryRepository.findById(recipe.getCategory_id());
                                        recipe.setCategory_key(category.getKey());
                                        remoteDatabase.writeNewRecipe(recipe);
                                        addRecipeIngredientsToFirebase(recipe.getId(), recipe.getKey());
                                    }
                                }
                            }
                        });
                    }
                }

                remoteDatabase.getRecipesList().addOnCompleteListener(new OnCompleteListener<List<RecipeFb>>() {

                    @Override
                    public void onComplete(@NonNull Task<List<RecipeFb>> task) {
                        List<RecipeFb> recipes = task.getResult();
                        for(RecipeFb recipe: recipes) {
                            Recipe recipeInDb = recipeRepository.findByKey(recipe.getKey());
                            if(recipeInDb == null){
                                if(isInTrash(recipe.getKey(), recipe.getModification_date())){
                                    remoteDatabase.deleteRecipe(recipe);
                                    Trash trashFirebase = new Trash("Recipe", recipe.getKey(), currentTime);
                                    firebaseTrash.add(trashFirebase);
                                }else{
                                    recipeInDb = new Recipe();
                                    recipeInDb.map(recipe);
                                    Category category = categoryRepository.findByKey(recipeInDb.getCategory_key());
                                    if(category!=null)
                                        recipeInDb.setCategory_id(category.getId());
                                    long recipe_id = recipeRepository.insertAll(recipeInDb)[0];
                                    addRecipeIngredientsToDatabase(recipe_id, recipe.getKey());
                                }
                            }else if(recipe.getModification_date() > recipeInDb.getModification_date()){
                                recipeInDb.map(recipe);
                                Category category = categoryRepository.findByKey(recipeInDb.getCategory_key());
                                if(category!=null)
                                    recipeInDb.setCategory_id(category.getId());
                                recipeRepository.updateAll(recipeInDb);
                                addRecipeIngredientsToDatabase(recipeInDb.getId(), recipe.getKey());
                            }else if(recipe.getModification_date() < recipeInDb.getModification_date()){
                                remoteDatabase.updateRecipe(recipeInDb);
                                updateRecipeIngredientsToFirebase(recipeInDb.getId(), recipe.getKey());
                            }
                        }


                    }
                });
                return "Synchronizacja zakończona pomyślnie";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                remoteDatabase.setTrashList(firebaseTrash);
                setNewSyncDates();
            }
        }.execute();
    }

    private void addRecipeIngredientsToDatabase(long recipe_id, String recipe_key) {
        remoteDatabase.getIngredientsForRecipe(recipe_key).addOnCompleteListener(new OnCompleteListener<List<RecipeIngredientFb>>() {
            @Override
            public void onComplete(@NonNull Task<List<RecipeIngredientFb>> task) {
                recipeIngredientRepository.deleteRecipeWithIngredients(recipe_id);
                for(RecipeIngredientFb recipeIngredientFb: task.getResult()){
                    Recipe_Ingredient recipe_ingredient = new Recipe_Ingredient();
                    recipe_ingredient.map(recipeIngredientFb);
                    recipe_ingredient.setRecipe_id(recipe_id);
                    recipeIngredientRepository.insertAll(recipe_ingredient);
                }
            }
        });
    }

    private void addRecipeIngredientsToFirebase(long recipe_id, String recipe_key) {
        List<Recipe_Ingredient> recipe_ingredients = recipeIngredientRepository.getIngredientsForRecipe(recipe_id);
        for(Recipe_Ingredient recipe_ingredient: recipe_ingredients){
            recipe_ingredient.setRecipe_key(recipe_key);
            remoteDatabase.writeNewRecipe_Ingredient(recipe_ingredient);
        }

    }

    private void updateRecipeIngredientsToFirebase(long recipe_id, String recipe_key) {
        //first remove all ingredients
        remoteDatabase.getIngredientsForRecipe(recipe_key).addOnCompleteListener(new OnCompleteListener<List<RecipeIngredientFb>>() {
            @Override
            public void onComplete(@NonNull Task<List<RecipeIngredientFb>> task) {
                for(RecipeIngredientFb recipeIngredientFb: task.getResult()){
                    remoteDatabase.deleteRecipe_Ingredient(recipeIngredientFb);
                }
                //insert new
                addRecipeIngredientsToFirebase(recipe_id, recipe_key);
            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private void syncShoppinglists(){
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                List<ShoppingList> shoppingLists = shoppinglistRepository.getAll();
                for(ShoppingList shoppingList: shoppingLists)
                    if(shoppingList.getKey() == null){
                        remoteDatabase.writeNewShoppinglist(shoppingList);
                        addShoppinglistIngredientsToFirebase(shoppingList.getKey(), shoppingList.getId());
                    }
                    else{
                        remoteDatabase.getShoppinglistByKey(shoppingList.getKey()).addOnCompleteListener(new OnCompleteListener<ShoppinglistFb>() {
                            @Override
                            public void onComplete(@NonNull Task<ShoppinglistFb> task) {
                                if(task.getResult() == null){
                                    remoteDatabase.writeNewShoppinglist(shoppingList);
                                    addShoppinglistIngredientsToFirebase(shoppingList.getKey(), shoppingList.getId());
                                }
                            }
                        });

                        remoteDatabase.getShoppinglistList().addOnCompleteListener(new OnCompleteListener<List<ShoppinglistFb>>() {
                            @Override
                            public void onComplete(@NonNull Task<List<ShoppinglistFb>> task) {
                                List<ShoppinglistFb> shoppingLists = task.getResult();
                                for(ShoppinglistFb shoppingList: shoppingLists) {
                                    ShoppingList shoppingListInDb = shoppinglistRepository.findByKey(shoppingList.getKey());
                                    if(shoppingListInDb == null){
                                        if(isInTrash(shoppingList.getKey(), shoppingList.getModification_date())){
                                            remoteDatabase.deleteShoppinglist(shoppingList);
                                            Trash trashFirebase = new Trash("Shoppinglist", shoppingList.getKey(), currentTime);
                                            firebaseTrash.add(trashFirebase);
                                        }else{
                                            shoppingListInDb = new ShoppingList();
                                            shoppingListInDb.map(shoppingList);
                                            long shoppinglist_id = shoppinglistRepository.insertAll(shoppingListInDb)[0];
                                            addShoppinglistIngredientsToDatabase(shoppingList.getKey(), shoppinglist_id);
                                        }
                                    }else if(shoppingListInDb.getModification_date() < shoppingList.getModification_date()){
                                        shoppingListInDb.map(shoppingList);
                                        shoppinglistRepository.update(shoppingListInDb);
                                        addShoppinglistIngredientsToDatabase(shoppingList.getKey(), shoppingListInDb.getId());
                                    }else if(shoppingListInDb.getModification_date() > shoppingList.getModification_date()){
                                        remoteDatabase.updateShoppinglist(shoppingListInDb);
                                        updateShoppinglistIngredientsInFirebase(shoppingList.getKey(), shoppingListInDb.getId());

                                    }
                                }
                            }
                        });
                    }
                return "Przeprowadzono synchronizację";
            }

        }.execute();

    }

    private void updateShoppinglistIngredientsInFirebase(String key, long id) {
        //first remove all ingredients
        remoteDatabase.getIngredientsForShoppinglist(key).addOnCompleteListener(new OnCompleteListener<List<ShoppinglistIngredientFb>>() {
            @Override
            public void onComplete(@NonNull Task<List<ShoppinglistIngredientFb>> task) {
                for(ShoppinglistIngredientFb shoppinglistIngredientFb: task.getResult()){
                    remoteDatabase.deleteShoppinglist_Ingredient(shoppinglistIngredientFb);
                }
                //insert new
                addShoppinglistIngredientsToFirebase(key, id);
            }
        });
    }

    private void addShoppinglistIngredientsToFirebase(String key, long id) {
        List<ShoppingList_Ingredient> shoppingList_ingredients = shoppinglistIngredientRepository.getIngredientsForShoppinglist(id);
        for(ShoppingList_Ingredient shoppingList_ingredient: shoppingList_ingredients){
            shoppingList_ingredient.setShoppinglist_key(key);
            remoteDatabase.writeNewShoppinglist_Ingredient(shoppingList_ingredient);
        }
    }

    private void addShoppinglistIngredientsToDatabase(String key, long id) {
        remoteDatabase.getIngredientsForShoppinglist(key).addOnCompleteListener(new OnCompleteListener<List<ShoppinglistIngredientFb>>() {
            @Override
            public void onComplete(@NonNull Task<List<ShoppinglistIngredientFb>> task) {
                shoppinglistIngredientRepository.deleteShoppinglistWithIngredients(id);
                for(ShoppinglistIngredientFb shoppinglistIngredientFb: task.getResult()){
                    ShoppingList_Ingredient shoppingList_ingredient = new ShoppingList_Ingredient();
                    shoppingList_ingredient.map(shoppinglistIngredientFb);
                    shoppingList_ingredient.setShoppinglist_id(id);
                    shoppinglistIngredientRepository.insertAll(shoppingList_ingredient);
                }
            }
        });
    }

    private boolean isInTrash(String key, long mod_date) {
        Trash trash = trashRepository.getByKey(key);
        return trash != null && trash.getDate() > mod_date;
    }

    private boolean isInFirebaseTrash(String key, long mod_date) {
        for(Trash trash: firebaseTrash){
            if(trash.getKey().equals(key)){
                return trash.getDate() > mod_date;
            }
        }
        return false;
    }

    private void setNewSyncDates() {
        remoteDatabase.setDatabaseSyncDate(currentTime);
        databaseDate.setSync_date(currentTime);
        databaseDateRepository.update(databaseDate);
    }

}
