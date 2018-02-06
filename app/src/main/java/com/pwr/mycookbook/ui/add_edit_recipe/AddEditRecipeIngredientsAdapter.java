package com.pwr.mycookbook.ui.add_edit_recipe;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.Trash;
import com.pwr.mycookbook.data.service_db.AppDatabase;

import java.util.Calendar;
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
            holder.remove = row.findViewById(R.id.item_recipe_ingredient_remove_button);
            holder.edit = row.findViewById(R.id.item_recipe_ingredient_edit_button);
            row.setTag(holder);
        }
        else {
            holder = (AddEditRecipeIngredientsAdapter.IngredientHolder) row.getTag();
        }

        Recipe_Ingredient ingredient = ingredients.get(position);
        holder.name.setText(ingredient.getName());
        holder.image.setImageResource(R.drawable.vegetarian_food_filled50);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();
                Trash trash = new Trash("RecipeIngredient", ingredient.getKey(), currentTime);
                AppDatabase db = AppDatabase.getAppDatabase(context);
                db.recipe_ingredientDao().delete(ingredient);
                db.trashDao().insert(trash);
                ingredients.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(ingredient);
            }
        });
        return row;
    }

    private void showDialog(Recipe_Ingredient ingredient){
        FragmentManager fm = ((AddEditRecipeActivity)context).getSupportFragmentManager();
        EditRecipeIngredientFragment editRecipeIngredientFragment = EditRecipeIngredientFragment.newInstance(ingredient);
        editRecipeIngredientFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                notifyDataSetChanged();
            }
        });
        editRecipeIngredientFragment.show(fm, "Edit ingredient");
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView name;
        private ImageButton remove;
        private ImageButton edit;
    }

}
