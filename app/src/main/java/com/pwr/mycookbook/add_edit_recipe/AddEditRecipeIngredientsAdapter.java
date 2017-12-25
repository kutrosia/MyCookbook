package com.pwr.mycookbook.add_edit_recipe;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.tables.Ingredient;
import com.pwr.mycookbook.tables.Recipe_Ingredient;

import java.util.List;

/**
 * Created by olaku on 05.12.2017.
 */

public class AddEditRecipeIngredientsAdapter extends ArrayAdapter<Recipe_Ingredient>{

    private Context context;
    private int layoutResourceId;
    private List<Recipe_Ingredient> ingredients;

    public AddEditRecipeIngredientsAdapter(@NonNull Context context, int layoutResourceId, List<Recipe_Ingredient> ingredients){
        super(context, layoutResourceId, R.id.item_recipe_ingredient_name, ingredients);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.ingredients = ingredients;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        super.getView(position, convertView, parent);

        View row = convertView;
        AddEditRecipeIngredientsAdapter.IngredientHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AddEditRecipeIngredientsAdapter.IngredientHolder();
            holder.name = row.findViewById(R.id.item_recipe_ingredient_name);
            holder.image = row.findViewById(R.id.item_recipe_ingredient_icon);
            holder.quantity = row.findViewById(R.id.item_recipe_ingredient_quantity);
            holder.measure = row.findViewById(R.id.item_recipe_ingredient_measure);
            holder.edit = row.findViewById(R.id.item_recipe_ingredient_edit_button);
            holder.remove = row.findViewById(R.id.item_recipe_ingredient_remove_button);
            row.setTag(holder);
        }
        else {
            holder = (AddEditRecipeIngredientsAdapter.IngredientHolder) row.getTag();
        }

        Recipe_Ingredient ingredient = ingredients.get(position);
        Ingredient ingredient1 = AppDatabase.getAppDatabase(getContext()).ingredientDao().findById(ingredient.ingredient_id);
        holder.name.setText(ingredient1.getName());
        holder.image.setImageResource(R.drawable.ingredients20);
        holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.measure.setText(ingredient.getMeasure());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return row;
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView name;
        private TextView quantity;
        private TextView measure;
        private ImageButton edit;
        private ImageButton remove;
    }

}
