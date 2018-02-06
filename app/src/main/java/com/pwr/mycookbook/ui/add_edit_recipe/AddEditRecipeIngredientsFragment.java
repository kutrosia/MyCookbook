package com.pwr.mycookbook.ui.add_edit_recipe;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;
import com.pwr.mycookbook.data.service_db.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaku on 27.11.2017.
 */

public class AddEditRecipeIngredientsFragment extends Fragment implements IRecipeSave {

    private Recipe recipe;
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeRepository recipeRepository;
    private static final String EXTRA_RECIPE = "recipe";
    private ListView listView;
    private AddEditRecipeIngredientsAdapter adapter;
    private EditText ingredient_name_EditText;
    private ImageButton ingredient_add_button;
    private List<Recipe_Ingredient> recipe_ingredients;


    public static AddEditRecipeIngredientsFragment newInstance(Recipe recipe) {
        AddEditRecipeIngredientsFragment result = new AddEditRecipeIngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE, recipe);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeIngredientRepository = new RecipeIngredientRepository(getContext());
        recipeRepository = new RecipeRepository(getContext());
        Bundle bundle = this.getArguments();
        recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);
        recipe_ingredients = recipeIngredientRepository.getIngredientsForRecipe(recipe.getId());
        if(recipe_ingredients == null)
            recipe_ingredients = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_edit_recipe_ingredients, container, false);
         ingredient_name_EditText = view.findViewById(R.id.item_recipe_ingredient_name);
         ingredient_add_button = view.findViewById(R.id.item_recipe_ingredient_add_button);
         listView = view.findViewById(R.id.shoppinglist_list_view);
         ingredient_add_button.setOnClickListener(onButtonAddClick());

        if(recipe.isImported())
            importIngredients();

        setRecipeInfo();

        return view;
    }

    private void importIngredients() {
        String ingredients_txt = recipe.getIngredients();
        if(ingredients_txt != null){
            String[] ingredients_set = ingredients_txt.split("[@]");
            Toast.makeText(getContext(), String.valueOf(ingredients_set.length), Toast.LENGTH_LONG).show();
            for(int i=0; i<ingredients_set.length; i++){
                addNewRecipeIngredientToList(ingredients_set[i]);
            }
            setRecipeInfo();
        }
    }

    private View.OnClickListener onButtonAddClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ingredient_name_EditText.getText().toString();
                addNewRecipeIngredientToList(name);
                ingredient_name_EditText.setText("");
            }
        };
    }

    private void addNewRecipeIngredientToList(String name) {
        if (name.equals("")) {
            Toast.makeText(getContext(), "Składnik musi posiadać nazwę", Toast.LENGTH_LONG).show();
        } else {
            Recipe_Ingredient recipe_ingredient = new Recipe_Ingredient();
            recipe_ingredient.setName(name);
            recipe_ingredient.setNew(true);
            recipe_ingredients.add(recipe_ingredient);
            setRecipeInfo();
        }
    }


    public void setRecipeInfo(){

       adapter = new AddEditRecipeIngredientsAdapter(getContext(),
                R.layout.list_recipe_ingredient_item, recipe_ingredients);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recipe != null){
            setRecipeInfo();
        }
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public void saveRecipe() {
        ((IRecipeChange) getActivity()).setRecipeIngredients(recipe_ingredients);
    }

    @Override
    public void setPhoto(Bitmap bitmap) {

    }
}
