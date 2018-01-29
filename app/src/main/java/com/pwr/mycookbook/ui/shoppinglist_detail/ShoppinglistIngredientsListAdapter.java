package com.pwr.mycookbook.ui.shoppinglist_detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;

import java.util.List;

/**
 * Created by olaku on 25.01.2018.
 */

public class ShoppinglistIngredientsListAdapter extends ArrayAdapter<ShoppingList_Ingredient> {

    private Context context;
    private int layoutResourceId;
    private List<ShoppingList_Ingredient> shoppingList_ingredients;
    private ShoppinglistIngredientRepository shoppinglistIngredientRepository;



    public ShoppinglistIngredientsListAdapter(Context context, int layoutResourceId, List<ShoppingList_Ingredient> shoppingList_ingredients) {
        super(context, layoutResourceId, shoppingList_ingredients);
        this.context = context;
        this.shoppingList_ingredients = shoppingList_ingredients;
        this.layoutResourceId = layoutResourceId;
        this.shoppinglistIngredientRepository = new ShoppinglistIngredientRepository(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ShoppinglistIngredientsListAdapter.IngredientsHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ShoppinglistIngredientsListAdapter.IngredientsHolder();
            holder.titleTextView = row.findViewById(R.id.ingredient_item_title);
            holder.image = row.findViewById(R.id.ingredient_item_image);
            holder.line = row.findViewById(R.id.line);

            row.setTag(holder);
        }
        else{
            holder = (ShoppinglistIngredientsListAdapter.IngredientsHolder) row.getTag();
        }
        ShoppingList_Ingredient shoppingList_ingredient = shoppingList_ingredients.get(position);
        if(shoppingList_ingredient.isToBuy()){
            holder.line.setVisibility(View.VISIBLE);
        }else{
            holder.line.setVisibility(View.INVISIBLE);
        }
        holder.titleTextView.setText(shoppingList_ingredient.getName());
        holder.image.setImageResource(R.drawable.vegetarian_food_filled50);

        IngredientsHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalHolder.line.getVisibility() == View.VISIBLE){
                    finalHolder.line.setVisibility(View.INVISIBLE);
                    shoppingList_ingredient.setToBuy(true);
                }else{
                    finalHolder.line.setVisibility(View.VISIBLE);
                    shoppingList_ingredient.setToBuy(false);
                }
                shoppinglistIngredientRepository.update(shoppingList_ingredient);
            }
        });

        return row;
    }


    private class IngredientsHolder{
        private ImageView image;
        private TextView titleTextView;
        private View line;

    }
}
