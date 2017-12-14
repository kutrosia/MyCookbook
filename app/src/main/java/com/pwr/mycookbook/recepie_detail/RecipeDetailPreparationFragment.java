package com.pwr.mycookbook.recepie_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.tables.*;

import java.io.Serializable;

/**
 * Created by olaku on 25.11.2017.
 */

public class RecipeDetailPreparationFragment extends Fragment{
    private Recipe recipe;
    private static final String EXTRA_RECIPE = "recipe";

    private TextView recipe_description_TextView;
    private TextView recipe_note_TextView;


    public static RecipeDetailPreparationFragment newInstance(Recipe recipe) {
        RecipeDetailPreparationFragment result = new RecipeDetailPreparationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE, (Serializable) recipe);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail_preparation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_description_TextView = (TextView) view.findViewById(R.id.recipe_description);
        recipe_note_TextView = (TextView) view.findViewById(R.id.recipe_note);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recipe != null){
            setRecipePreparation();
        }
    }

    private void setRecipePreparation() {
        if(recipe != null){
            recipe_description_TextView.setText(recipe.getDescription());
            recipe_note_TextView.setText(recipe.getNote());
        }
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

}
