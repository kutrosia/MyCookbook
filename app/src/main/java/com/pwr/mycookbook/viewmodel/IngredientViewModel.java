package com.pwr.mycookbook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pwr.mycookbook.model.dao.IngredientDao;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.repository.AppDatabase;
import com.pwr.mycookbook.model.repository.IngredientLocalRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 21.12.2017.
 */

public class IngredientViewModel extends ViewModel {

    IngredientLocalRepository ingredientLocalRepository;

    public IngredientViewModel(IngredientLocalRepository ingredientLocalRepository) {
        this.ingredientLocalRepository = ingredientLocalRepository;
    }

    public Flowable<List<Ingredient>> getAll(){
        return ingredientLocalRepository.getAll();
    }

    public Maybe<Ingredient> findById(long ingredient_id){
        return ingredientLocalRepository.findById(ingredient_id);
    }

    public void insert(Ingredient ingredient){
        ingredientLocalRepository.insert(ingredient);
    }

    public void update(Ingredient ingredient){
        ingredientLocalRepository.update(ingredient);
    }

    public void delete(Ingredient ingredient){
        ingredientLocalRepository.delete(ingredient);
    }

}
