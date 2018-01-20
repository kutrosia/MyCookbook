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
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;

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

            row.setTag(holder);
        }
        else {
            holder = (IngredientHolder) row.getTag();
        }

        Recipe_Ingredient ingredient = ingredients.get(position);
        holder.nameTextView.setText(ingredient.getName());
        holder.image.setImageResource(R.drawable.vegetarian_food_filled50);

        return row;
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView nameTextView;

    }
}
