package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.Recipe_IngredientDao;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class RecipeIngredientRepository {

    private Context context;
    private AppDatabase db;
    private Recipe_IngredientDao recipe_ingredientDao;
    private String type = "RecipeIngredient";

    public RecipeIngredientRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.recipe_ingredientDao = db.recipe_ingredientDao();
    }


    public void insertAll(Recipe_Ingredient recipe_ingredient) {
        long currentTime = getCurrentTime();
        recipe_ingredient.setModification_date(currentTime);
        setUpdateDate(currentTime);
        recipe_ingredientDao.insertAll(recipe_ingredient);
    }

    public void update(Recipe_Ingredient recipe_ingredient) {
        long currentTime = getCurrentTime();
        recipe_ingredient.setModification_date(currentTime);
        setUpdateDate(currentTime);
        recipe_ingredientDao.update(recipe_ingredient);
    }

    public void delete(Recipe_Ingredient recipe_ingredient) {
        addToTrash(recipe_ingredient);
        setUpdateDate(getCurrentTime());
        recipe_ingredientDao.delete(recipe_ingredient);
    }

    public List<Recipe_Ingredient> getIngredientsForRecipe(long recipe_id) {
        return recipe_ingredientDao.getIngredientsForRecipe(recipe_id);
    }

    public List<Recipe_Ingredient> getAll() {
        return recipe_ingredientDao.getAll();
    }

    public List<Recipe_Ingredient> getNotModified(long modify_date) {
        return recipe_ingredientDao.getNotModified(modify_date);
    }

    public Recipe_Ingredient findByKey(String key) {
        return recipe_ingredientDao.findByKey(key);
    }

    public void deleteRecipeWithIngredients(long recipe_id) {
        recipe_ingredientDao.deleteRecipeWithIngredients(recipe_id);
    }

    public void deleteIngredientInRecipe(long recipe_id, long ingredient_id) {
        recipe_ingredientDao.deleteIngredientInRecipe(recipe_id, ingredient_id);
    }

    public int getCount() {
        return recipe_ingredientDao.getCount();
    }

    public long getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public void setUpdateDate(long currentTime){
        DatabaseDate databaseDate = db.databaseDateDao().getAll();
        if(databaseDate == null){
            databaseDate = new DatabaseDate();
            databaseDate.setModified_date(currentTime);
            db.databaseDateDao().insertAll(databaseDate);
        }else{
            databaseDate.setModified_date(currentTime);
            db.databaseDateDao().update(databaseDate);
        }
    }

    public void addToTrash(Recipe_Ingredient recipe_ingredient){
        Trash trash = new Trash(type, recipe_ingredient.getKey(), getCurrentTime());
        db.trashDao().insert(trash);
    }
}
