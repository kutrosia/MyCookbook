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
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.model.model.Recipe_Ingredient;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;
import com.pwr.mycookbook.viewmodel.Recipe_IngredientListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 25.11.2017.
 */

public class RecipeDetailIngredientsFragment extends Fragment {
    private Recipe mRecipe;
    private static final String EXTRA_RECIPE = "recipe";
    private RecipeDetailIngredientsAdapter adapter;
    private ListView ingredients_list_view;

    private List<Recipe_Ingredient> recipe_ingredientList;
    private List<Ingredient> ingredientList;

    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    private RecipeViewModel recipeViewModel;

    public static RecipeDetailIngredientsFragment newInstance(long recipe_id) {
        RecipeDetailIngredientsFragment result = new RecipeDetailIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_RECIPE, recipe_id);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        long recipe_id = bundle.getLong(EXTRA_RECIPE);
        recipeViewModel = ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel.class);
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
        return inflater.inflate(R.layout.fragment_recipe_detail_ingredients, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredients_list_view = view.findViewById(R.id.recipe_ingredients);
       /* Recipe_IngredientListViewModel.Factory factory = new Recipe_IngredientListViewModel.Factory(
                getActivity().getApplication(), mRecipe.getId());
        Recipe_IngredientListViewModel recipe_ingredientListViewModel = ViewModelProviders.of(RecipeDetailIngredientsFragment.this, factory).get(Recipe_IngredientListViewModel.class);
        recipe_ingredientListViewModel.getRecipe_ingredients().observe(RecipeDetailIngredientsFragment.this, new Observer<List<Recipe_Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Recipe_Ingredient> recipe_ingredients) {
                recipe_ingredientList = recipe_ingredients;

            }
        });
        recipe_ingredientListViewModel.getIngredients().observe(RecipeDetailIngredientsFragment.this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                ingredientList = ingredients;
            }
        });

        adapter = new RecipeDetailIngredientsAdapter(
                getContext(), R.layout.list_recipe_detail_ingredients_item, recipe_ingredientList, ingredientList);
        ingredients_list_view.setAdapter(adapter);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setRecipe(Recipe mRecipe){
        this.mRecipe = mRecipe;
    }

}
