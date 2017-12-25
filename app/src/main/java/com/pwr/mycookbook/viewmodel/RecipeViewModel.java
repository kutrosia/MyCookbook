package com.pwr.mycookbook.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.dao.RecipeDao;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.model.repository.AppDatabase;
import com.pwr.mycookbook.model.repository.RecipeLocalRepository;
import com.pwr.mycookbook.model.repository.RecipeLocalRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by olaku on 20.12.2017.
 */

public class RecipeViewModel extends ViewModel {

    RecipeLocalRepository recipeLocalRepository;

    public RecipeViewModel(RecipeLocalRepository recipeLocalRepository) {
        this.recipeLocalRepository = recipeLocalRepository;
    }

    public Flowable<List<Recipe>> getAll(){
        return recipeLocalRepository.getAll();
    }

    public Flowable<List<Recipe>> getAllByCategory(long category_id){
        return recipeLocalRepository.findAllByCategory(category_id);
    }

    public Maybe<Recipe> findById(long recipe_id){
        return recipeLocalRepository.findById(recipe_id);
    }

    public long[] insert(Recipe recipe){
        return recipeLocalRepository.insert(recipe);
    }

    public void update(Recipe recipe){
        recipeLocalRepository.update(recipe);
    }

    public void delete(Recipe recipe){
        recipeLocalRepository.delete(recipe);
    }

}
