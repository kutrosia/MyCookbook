package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.DatabaseDateDao;
import com.pwr.mycookbook.data.dao.RecipeDao;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class RecipeRepository{

    private Context context;
    private AppDatabase db;
    private RecipeDao recipeDao;
    private DatabaseDateDao databaseDateDao;
    private String type = "Recipe";

    public RecipeRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.recipeDao = db.recipeDao();
        this.databaseDateDao = db.databaseDateDao();
    }

    public long[] insertAll(Recipe recipe) {
        long currentTime = getCurrentTime();
        recipe.setModification_date(currentTime);
        setUpdateDate(currentTime);
        return recipeDao.insertAll(recipe);
    }

    public void updateAll(Recipe recipe) {
        long currentTime = getCurrentTime();
        recipe.setModification_date(currentTime);
        setUpdateDate(currentTime);
        recipeDao.updateAll(recipe);
    }

    public void deleteAll(Recipe recipe) {
        addToTrash(recipe);
        setUpdateDate(getCurrentTime());
        recipeDao.deleteAll(recipe);
    }

    public List<Recipe> getAll() {
        return recipeDao.getAll();
    }

    public List<Recipe> getNotModified(long modify_date) {
        return recipeDao.getNotModified(modify_date);
    }

    public Recipe findById(long recipe_id) {
        return recipeDao.findById(recipe_id);
    }

    public Recipe findByKey(String key) {
        return recipeDao.findByKey(key);
    }

    public List<Recipe> findAllByCategory(long category_id) {
        return recipeDao.findAllByCategory(category_id);
    }

    public List<Recipe> filterByCategory() {
        return recipeDao.filterByCategory();
    }

    public List<Recipe> filterByTitle() {
        return recipeDao.filterByTitle();
    }

    public List<Recipe> filterByTime() {
        return recipeDao.filterByTime();
    }

    public List<Recipe> getAllWithSubstring(String word1, String word2, String word3) {
        return recipeDao.getAllWithSubstring(word1, word2, word3);
    }

    public List<Recipe> getAllWithIngredients(String ingredient1, String ingredient2, String ingredient3) {
        return recipeDao.getAllWithIngredients(ingredient1, ingredient2, ingredient3);
    }

    public List<Recipe> getAllMovies(){
        List<Recipe> recipes = recipeDao.getAll();
        List<Recipe> recipesWithMovies = new ArrayList<>();
        for(Recipe recipe: recipes){
            if(recipe.getMovie() != null){
                recipesWithMovies.add(recipe);
            }
        }
        return recipesWithMovies;
    }

    public int getCount() {
        return recipeDao.getCount();
    }

    public long getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public void setUpdateDate(long currentTime){
        DatabaseDate databaseDate = databaseDateDao.getAll();
        if(databaseDate == null){
            databaseDate = new DatabaseDate();
            databaseDate.setModified_date(currentTime);
            databaseDateDao.insertAll(databaseDate);
        }else{
            databaseDate.setModified_date(currentTime);
            databaseDateDao.update(databaseDate);
        }
    }

    public void addToTrash(Recipe recipe){
        Trash trash = new Trash(type, recipe.getKey(), getCurrentTime());
        db.trashDao().insert(trash);
    }
}
