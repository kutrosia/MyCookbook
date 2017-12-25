package com.pwr.mycookbook.lists;

import android.app.Activity;
import android.content.Context;
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
import com.pwr.mycookbook.tables.Recipe;

import java.io.File;
import java.util.List;

/**
 * Created by olaku on 04.12.2017.
 */

public class RecipeListAdapter extends ArrayAdapter<Recipe> {


    private Context context;
    private int layoutResourceId;
    private List<Recipe> recipeList;



    public RecipeListAdapter(Context context, int layoutResourceId, List<Recipe> recipeList) {
        super(context, layoutResourceId, recipeList);
        this.context = context;
        this.recipeList = recipeList;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        RecipeListAdapter.RecipeHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecipeListAdapter.RecipeHolder();
            holder.titleTextView = row.findViewById(R.id.recipe_item_title);
            holder.image = row.findViewById(R.id.recipe_item_image);

            row.setTag(holder);
        }
        else{
            holder = (RecipeListAdapter.RecipeHolder) row.getTag();
        }

        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        try{
            holder.image.setImageURI(Uri.fromFile(new File(recipe.getPhoto())));
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.noodles50);
        }

        return row;
    }

    private class RecipeHolder{
        private ImageView image;
        private TextView titleTextView;

    }
}
