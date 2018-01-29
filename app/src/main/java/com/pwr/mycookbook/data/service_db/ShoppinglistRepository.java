package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.ShoppingListDao;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class ShoppinglistRepository {

    private Context context;
    private AppDatabase db;
    private ShoppingListDao shoppingListDao;
    private String type = "Shoppinglist";

    public ShoppinglistRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.shoppingListDao = db.shoppingListDao();
    }

    public long[] insertAll(ShoppingList shoppingList) {
        long currentTime = getCurrentTime();
        shoppingList.setModification_date(currentTime);
        setUpdateDate(currentTime);
        return shoppingListDao.insertAll(shoppingList);
    }

    public void update(ShoppingList shoppingList) {
        long currentTime = getCurrentTime();
        shoppingList.setModification_date(currentTime);
        setUpdateDate(currentTime);
        shoppingListDao.update(shoppingList);
    }

    public void delete(ShoppingList shoppingList) {
        addToTrash(shoppingList);
        setUpdateDate(getCurrentTime());
        shoppingListDao.delete(shoppingList);
    }

    public List<ShoppingList> getAll() {
        return shoppingListDao.getAll();
    }

    public List<ShoppingList> getNotModified(long modify_date) {
        return shoppingListDao.getNotModified(modify_date);
    }

    public ShoppingList findByKey(String key) {
        return shoppingListDao.findByKey(key);
    }

    public ShoppingList findById(long shoppinglist_id) {
        return shoppingListDao.findById(shoppinglist_id);
    }

    public int getCount() {
        return shoppingListDao.getCount();
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

    public void addToTrash(ShoppingList shoppingList){
        Trash trash = new Trash(type, shoppingList.getKey(), getCurrentTime());
        db.trashDao().insert(trash);
    }
}
