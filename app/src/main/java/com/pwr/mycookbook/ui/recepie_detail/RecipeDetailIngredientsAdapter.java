package com.pwr.mycookbook.ui.recepie_detail;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.service.AppDatabase;
import com.pwr.mycookbook.data.model.Ingredient;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;

import java.util.List;

/**
 * Created by olaku on 04.12.2017.
 */

public class RecipeDetailIngredientsAdapter extends ArrayAdapter<Recipe_Ingredient> {

    private Context context;
    private List<Recipe_Ingredient> ingredients;
    private int layoutResourceId;

    public RecipeDetailIngredientsAdapter(@NonNull Context context, int layoutResourceId,
                                          List<Recipe_Ingredient> ingredients) {
        super(context, layoutResourceId, ingredients);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        IngredientHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new IngredientHolder();
            holder.nameTextView = row.findViewById(R.id.item_recipe_ingredient_name);
            holder.image = row.findViewById(R.id.item_recipe_ingredient_icon);
            holder.quantityTextView = row.findViewById(R.id.item_recipe_ingredient_quantity);
            holder.measureTextView = row.findViewById(R.id.item_recipe_ingredient_measure);

            row.setTag(holder);
        }
        else {
            holder = (IngredientHolder) row.getTag();
        }

        Recipe_Ingredient ingredient = ingredients.get(position);
        Ingredient ingredient1 = AppDatabase.getAppDatabase(getContext()).ingredientDao().findById(ingredient.ingredient_id);
        holder.nameTextView.setText(ingredient1.getName());
        holder.image.setImageResource(R.drawable.dossier_25);
        holder.quantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureTextView.setText(ingredient.getMeasure());

        return row;
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView nameTextView;
        private TextView quantityTextView;
        private TextView measureTextView;
    }
}
