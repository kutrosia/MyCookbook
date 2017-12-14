package com.pwr.mycookbook.add_edit_recipe;

import android.annotation.SuppressLint;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.tables.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by olaku on 27.11.2017.
 */

public class AddEditRecipeIngredientsFragment extends Fragment {

    private Recipe recipe;
    private AppDatabase db;
    private static final String EXTRA_RECIPE = "recipe";
    private ListView listView;
    private AddEditRecipeIngredientsAdapter adapter;
    private IngredientIconsListAdapter iconsListAdapter;
    private Spinner icons_spinner;
    private EditText ingredient_name_EditText;
    private EditText ingredient_quantity_EditText;
    private EditText ingredient_measure_EditText;
    private ImageButton ingredient_add_button;
    private Integer[] iconsRes;


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
        db = AppDatabase.getAppDatabase(getContext());
        Bundle bundle = this.getArguments();
        recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_edit_recipe_ingredients, container, false);
         icons_spinner = view.findViewById(R.id.item_recipe_ingredient_icon_spinner);
         ingredient_name_EditText = view.findViewById(R.id.item_recipe_ingredient_name);
         ingredient_quantity_EditText = view.findViewById(R.id.item_recipe_ingredient_quantity);
         ingredient_measure_EditText = view.findViewById(R.id.item_recipe_ingredient_measure);
         ingredient_add_button = view.findViewById(R.id.item_recipe_ingredient_add_button);

         ingredient_add_button.setOnClickListener(onButtonAddClick());
         setIconsToSpinner();

        return view;
    }

    private void setIconsToSpinner(){
        iconsRes = new Integer[]{
                R.drawable.noodles_black_white50,
                R.drawable.pizza50,
                R.drawable.rice_bowl_filled50,
                R.drawable.steak50,
                R.drawable.sushi50,
                R.drawable.food_and_wine50,
                R.drawable.cupcake_filled50,
                R.drawable.cake_filled50,
                R.drawable.pancake_filled50
        };

        iconsListAdapter = new IngredientIconsListAdapter(getContext(), R.layout.list_category_icon_item, iconsRes);
        icons_spinner.setAdapter(iconsListAdapter);
    }

    private View.OnClickListener onButtonAddClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ingredient_name_EditText.getText().toString();
                int quantity = Integer.parseInt(ingredient_quantity_EditText.getText().toString());
                String measure = ingredient_measure_EditText.getText().toString();

                if(name.equals("")){
                    Toast.makeText(getContext(), "Składnik musi posiadać nazwę", Toast.LENGTH_LONG).show();
                }else{

                    Ingredient ingredient = checkIfNewIgredient(name);
                    long ingredient_id;
                    if(ingredient == null){
                        ingredient = new Ingredient();
                        ingredient.setName(name);
                        ingredient_id = db.ingredientDao().insertAll(ingredient)[0];
                    }else
                        ingredient_id = ingredient.getId();
                    Recipe_Ingredient recipe_ingredient = new Recipe_Ingredient(recipe.getId(), ingredient_id);
                    recipe_ingredient.setMeasure(measure);
                    recipe_ingredient.setQuantity(quantity);
                    db.recipe_ingredientDao().insertAll(recipe_ingredient);
                    setRecipeInfo();
                }
            }
        };
    }

    private Ingredient checkIfNewIgredient(String name) {
        List<Ingredient> ingredients = db.ingredientDao().getAll();
        for(Ingredient ingredient: ingredients){
            if(ingredient.getName().equals(name)){
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.ingredients_list_view);
        setRecipeInfo();
    }


    @SuppressLint("StaticFieldLeak")
    public void setRecipeInfo(){
        if(recipe != null){
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected Void doInBackground(Void... voids) {
                    List<Recipe_Ingredient> recipe_ingredients =
                            db.recipe_ingredientDao().getIngredientsForRecipe(recipe.getId());
                    adapter = new AddEditRecipeIngredientsAdapter(getContext(),
                            R.layout.list_recipe_ingredient_item, recipe_ingredients);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    listView.setAdapter(adapter);

                }

            }.execute();
        }

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

}
