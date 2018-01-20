package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.ShoppingList_IngredientDao;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class ShoppinglistIngredientRepository {

    private Context context;
    private AppDatabase db;
    private ShoppingList_IngredientDao shoppingList_ingredientDao;
    private String type = "ShoppingIngredient";

    public ShoppinglistIngredientRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.shoppingList_ingredientDao = db.shoppingList_ingredientDao();
    }

    public void insertAll(ShoppingList_Ingredient shoppinglist_ingredient) {
        long currentTime = getCurrentTime();
        shoppinglist_ingredient.setModification_date(currentTime);
        setUpdateDate(currentTime);
        shoppingList_ingredientDao.insertAll(shoppinglist_ingredient);
    }

    public void update(ShoppingList_Ingredient shoppinglist_ingredient) {
        long currentTime = getCurrentTime();
        shoppinglist_ingredient.setModification_date(currentTime);
        setUpdateDate(currentTime);
        shoppingList_ingredientDao.update(shoppinglist_ingredient);
    }

    public void delete(ShoppingList_Ingredient shoppinglist_ingredients) {
        addToTrash(shoppinglist_ingredients);
        setUpdateDate(getCurrentTime());
        shoppingList_ingredientDao.delete(shoppinglist_ingredients);
    }

    public List<ShoppingList_Ingredient> getAll() {
        return shoppingList_ingredientDao.getAll();
    }

    public List<ShoppingList_Ingredient> getIngredientsForShoppinglist(long shoppinglist_id){
        return shoppingList_ingredientDao.getIngredientsForShoppinglist(shoppinglist_id);
    }


    public List<ShoppingList_Ingredient> getNotModified(long modify_date) {
        return shoppingList_ingredientDao.getNotModified(modify_date);
    }

    public ShoppingList_Ingredient findByKey(String key) {
        return shoppingList_ingredientDao.findByKey(key);
    }

    public void deleteShoppinglistWithIngredients(long shoppinglist_id) {
        shoppingList_ingredientDao.deleteShoppinglistWithIngredients(shoppinglist_id);
    }

    public void deleteIngredientInShoppinglist(long shoppinglist_id, long ingredient_id) {
        shoppingList_ingredientDao.deleteIngredientInShoppinglist(shoppinglist_id, ingredient_id);
    }

    public int getCount() {
        return shoppingList_ingredientDao.getCount();
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

    public void addToTrash(ShoppingList_Ingredient shoppingList_ingredient){
        Trash trash = new Trash(type, shoppingList_ingredient.getKey(), getCurrentTime());
        db.trashDao().insert(trash);
    }
}
