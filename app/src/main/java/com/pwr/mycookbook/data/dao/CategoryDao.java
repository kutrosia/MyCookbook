package com.pwr.mycookbook.data.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model.Category;

import java.util.List;

/**
 * Created by olaku on 02.12.2017.
 */

@Dao
public interface CategoryDao {

    @Insert
    void insertAll(Category... categories);

    @Update
    void update(Category... categories);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories where id LIKE :category_id")
    Category findById(long category_id);


    @Query("SELECT * FROM categories where modification_date < :modify_date")
    List<Category> getNotModified(long modify_date);


    @Query("SELECT COUNT(*) from categories")
    int countCategories();


}
