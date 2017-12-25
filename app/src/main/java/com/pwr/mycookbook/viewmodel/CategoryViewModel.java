package com.pwr.mycookbook.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.model.repository.CategoryLocalRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 20.12.2017.
 */

public class CategoryViewModel extends ViewModel {

    private CategoryLocalRepository categoryLocalRepository;


    public CategoryViewModel(CategoryLocalRepository localRepository) {
        this.categoryLocalRepository = localRepository;
    }

    public Flowable<List<Category>> getAll(){
        return categoryLocalRepository.getAll();
    }

    public Maybe<Category> findById(long category_id){
        return categoryLocalRepository.findById(category_id);
    }

    public void insert(Category category){
        categoryLocalRepository.insert(category);
    }

    public void update(Category category){
        categoryLocalRepository.update(category);
    }

    public void delete(Category category){
        categoryLocalRepository.delete(category);
    }

}
