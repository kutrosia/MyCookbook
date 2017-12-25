package com.pwr.mycookbook.model.repository;
import com.pwr.mycookbook.model.model.Category;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 22.12.2017.
 */

public interface CategoryLocalRepository {

    Flowable<List<Category>> getAll();
    Maybe<Category> findById(long category_id);
    void insert(Category category);
    void update(Category category);
    void delete(Category category);

}
