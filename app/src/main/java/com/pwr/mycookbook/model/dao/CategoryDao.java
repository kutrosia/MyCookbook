package com.pwr.mycookbook.model.dao;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.model.model.Category;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

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
    Flowable<List<Category>> getAll();

    @Query("SELECT * FROM categories where id LIKE  :category_id")
    Maybe<Category> findById(long category_id);


}
