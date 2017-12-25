package com.pwr.mycookbook.view.add_edit_recipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
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
 * Created by olaku on 27.11.2017.
 */

public class AddEditRecipeIngredientsFragment extends Fragment implements IRecipeSave {

    private Recipe mRecipe;
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
    private List<Recipe_Ingredient> recipe_ingredientList;
    private List<Ingredient> ingredientFullList;
    private List<Ingredient> ingredientList;

    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    private RecipeViewModel recipeViewModel;


    public static AddEditRecipeIngredientsFragment newInstance(long recipe_id) {
        AddEditRecipeIngredientsFragment result = new AddEditRecipeIngredientsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_add_edit_recipe_ingredients, container, false);
        icons_spinner = view.findViewById(R.id.item_recipe_ingredient_icon_spinner);
        ingredient_name_EditText = view.findViewById(R.id.item_recipe_ingredient_name);
        ingredient_quantity_EditText = view.findViewById(R.id.item_recipe_ingredient_quantity);
        ingredient_measure_EditText = view.findViewById(R.id.item_recipe_ingredient_measure);
        ingredient_add_button = view.findViewById(R.id.item_recipe_ingredient_add_button);

        ingredient_add_button.setOnClickListener(onButtonAddClick());
        setIconsToSpinner();
        setRecipeInfo();

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

                   /* Ingredient ingredient = checkIfNewIgredient(name);
                    long ingredient_id;
                    if(ingredient == null){
                        ingredient = new Ingredient();
                        ingredient.setName(name);
                        *//*IngredientListViewModel ingredientListViewModel = ViewModelProviders.of(AddEditRecipeIngredientsFragment.this).get(IngredientListViewModel.class);
                        ingredient_id = ingredientListViewModel.insertAll(ingredient)[0];*//*
                    }else
                        ingredient_id = ingredient.getId();
                    Recipe_Ingredient recipe_ingredient;
                    recipe_ingredient = new Recipe_Ingredient(-1, ingredient_id);
                    recipe_ingredient.setMeasure(measure);
                    recipe_ingredient.setQuantity(quantity);
                    recipe_ingredientList.add(recipe_ingredient);
                    setRecipeInfo();*/
                }

                ingredient_name_EditText.setText("");
                ingredient_quantity_EditText.setText("");
                ingredient_measure_EditText.setText("");
            }
        };
    }


    private Ingredient checkIfNewIgredient(String name) {
        /*IngredientListViewModel ingredientListViewModel = ViewModelProviders.of(this).get(IngredientListViewModel.class);
        ingredientFullList = ingredientListViewModel.getIngredientList().getValue();
        for(Ingredient ingredient: ingredientFullList){
            if(ingredient.getName().equals(name)){
                return ingredient;
            }
        }*/
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.ingredients_list_view);
        setRecipeInfo();
    }

    public void setRecipeInfo(){
        adapter = new AddEditRecipeIngredientsAdapter(getContext(),
                R.layout.list_recipe_ingredient_item, recipe_ingredientList, ingredientList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRecipe != null){
            setRecipeInfo();
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

    @Override
    public void saveRecipe() {
        //recipeViewModel.setRecipeMutableLiveData(mRecipe);
        recipeViewModel.update(mRecipe);
        //((IRecipeChange) getActivity()).setRecipeIngredients(recipe_ingredientList);
    }

    @Override
    public void setPhoto(Bitmap bitmap) {

    }
}
