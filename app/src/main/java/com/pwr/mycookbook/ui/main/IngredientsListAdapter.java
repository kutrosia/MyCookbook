package com.pwr.mycookbook.ui.main;

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
import com.pwr.mycookbook.data.model.Ingredient;

import java.util.List;

/**
 * Created by olaku on 05.12.2017.
 */

public class IngredientsListAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private int layoutResourceId;
    private List<Ingredient> ingredients;



    public IngredientsListAdapter(Context context, int layoutResourceId, List<Ingredient> ingredients) {
        super(context, layoutResourceId, ingredients);
        this.context = context;
        this.ingredients = ingredients;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        IngredientsListAdapter.IngredientHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new IngredientsListAdapter.IngredientHolder();
            holder.name = row.findViewById(R.id.ingredient_item_name);
            holder.image = row.findViewById(R.id.ingredient_item_image);

            row.setTag(holder);
        }
        else{
            holder = (IngredientsListAdapter.IngredientHolder) row.getTag();
        }

        Ingredient ingredient = ingredients.get(position);
        holder.name.setText(ingredient.getName());
        try{
            //holder.image.setImageURI(Uri.fromFile(new File(ingredient.getPhoto())));
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.dossier_25);
        }

        return row;
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView name;

    }

}
