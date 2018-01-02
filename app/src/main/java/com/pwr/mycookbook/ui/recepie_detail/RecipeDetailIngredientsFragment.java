package com.pwr.mycookbook.ui.recepie_detail;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.service.AppDatabase;
import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;

import java.util.List;

/**
 * Created by olaku on 25.11.2017.
 */

public class RecipeDetailIngredientsFragment extends Fragment {
    private Recipe recipe;
    private static final String EXTRA_RECIPE = "recipe";
    private AppDatabase db;
    private RecipeDetailIngredientsAdapter adapter;
    private ListView ingredients_list_view;

    public static RecipeDetailIngredientsFragment newInstance(Recipe recipe) {
        RecipeDetailIngredientsFragment result = new RecipeDetailIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE, recipe);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        this.recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);
        db = AppDatabase.getAppDatabase(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail_ingredients, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredients_list_view = view.findViewById(R.id.recipe_ingredients);
        new AsyncTask<Object, Void, String>(){

            @Override
            protected String doInBackground(Object... params) {
                try {
                    List<Recipe_Ingredient> ingredients = db.recipe_ingredientDao().getIngredientsForRecipe(recipe.getId());
                    adapter = new RecipeDetailIngredientsAdapter(
                            getContext(), R.layout.list_recipe_detail_ingredients_item, ingredients);
                }catch(Exception e){
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                ingredients_list_view.setAdapter(adapter);
            }
        }.execute(view.getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

}
