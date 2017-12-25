package com.pwr.mycookbook.view.recipe_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 25.11.2017.
 */

public class RecipeDetailPreparationFragment extends Fragment{
    private Recipe mRecipe;
    private static final String EXTRA_RECIPE = "recipe";

    private TextView recipe_description_TextView;
    private TextView recipe_note_TextView;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    private RecipeViewModel recipeViewModel;


    public static RecipeDetailPreparationFragment newInstance(long recipe_id) {
        RecipeDetailPreparationFragment result = new RecipeDetailPreparationFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_RECIPE, recipe_id);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeViewModel = ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel.class);
        Bundle bundle = this.getArguments();
        long recipe_id = bundle.getLong(EXTRA_RECIPE);
        getRecipeFromDB(recipe_id);
    }

    private void getRecipeFromDB(long recipe_id){
        recipeViewModel.findById(recipe_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe ->
                        mRecipe = recipe
                );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail_preparation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_description_TextView = view.findViewById(R.id.recipe_description);
        recipe_note_TextView = view.findViewById(R.id.recipe_note);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRecipe != null){
            setRecipePreparation();
        }
    }

    private void setRecipePreparation() {
        if(mRecipe != null){
            recipe_description_TextView.setText(mRecipe.getDescription());
            recipe_note_TextView.setText(mRecipe.getNote());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    public void setRecipe(Recipe recipe){
        this.mRecipe = recipe;
    }

}
