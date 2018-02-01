package com.pwr.mycookbook.ui.add_movie;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.ui.import_recipe_from_website.WebsiteImagesGalleryAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by olaku on 01.02.2018.
 */

public class MoviesGalleryAdapter extends BaseAdapter {

    List<Recipe> recipeList;
    int layoutResourceId;
    Context context;

    public MoviesGalleryAdapter(List<Recipe> recipeList, int layoutResourceId, Context context) {
        super();
        this.recipeList = recipeList;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        MoviesGalleryAdapter.MovieHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MoviesGalleryAdapter.MovieHolder();
            holder.image = row.findViewById(R.id.recipe_item_image);
            holder.title  = row.findViewById(R.id.recipe_title);

            row.setTag(holder);
        }
        else{
            holder = (MoviesGalleryAdapter.MovieHolder) row.getTag();
        }
        Recipe recipe = recipeList.get(position);
        holder.title.setText(recipe.getTitle());
        try{
            holder.image.setImageURI(Uri.fromFile(new File(recipe.getPhoto())));
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.play_button_filled50);
        }
        return row;
    }

    private class MovieHolder{
        private ImageView image;
        private TextView title;
    }
}

