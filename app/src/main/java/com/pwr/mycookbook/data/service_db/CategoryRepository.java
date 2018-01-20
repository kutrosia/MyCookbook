package com.pwr.mycookbook.data.service_db;

import android.content.Context;

import com.pwr.mycookbook.data.dao.CategoryDao;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.Trash;

import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 08.01.2018.
 */

public class CategoryRepository {

    private Context context;
    private AppDatabase db;
    private CategoryDao categoryDao;
    private String type = "Category";

    public CategoryRepository(Context context) {
        this.context = context;
        this.db = AppDatabase.getAppDatabase(context);
        this.categoryDao = db.categoryDao();
    }


    public void insertAll(Category category) {
        long currentTime = getCurrentTime();
        category.setModification_date(currentTime);
        setUpdateDate(currentTime);
        categoryDao.insertAll(category);
    }

    public void update(Category category) {
        long currentTime = getCurrentTime();
        category.setModification_date(currentTime);
        setUpdateDate(currentTime);
        categoryDao.update(category);
    }

    public void delete(Category category) {
        setUpdateDate(getCurrentTime());
        addToTrash(category);
        categoryDao.delete(category);
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    public Category findById(long category_id) {
        return categoryDao.findById(category_id);
    }

    public Category findByKey(String key) {
        return categoryDao.findByKey(key);
    }

    public List<Category> getNotModified(long modify_date) {
        return categoryDao.getNotModified(modify_date);
    }

    public int countCategories() {
        return categoryDao.countCategories();
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

    public void addToTrash(Category category){
        Trash trash = new Trash(type, category.getKey(), getCurrentTime());
        db.trashDao().insert(trash);
    }
}
