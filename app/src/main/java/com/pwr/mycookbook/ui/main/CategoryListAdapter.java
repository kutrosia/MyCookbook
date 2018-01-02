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
import com.pwr.mycookbook.data.model.Category;

import java.util.List;

/**
 * Created by olaku on 05.12.2017.
 */

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int layoutResourceId;
    private List<Category> categories;



    public CategoryListAdapter(Context context, int layoutResourceId, List<Category> categories) {
        super(context, layoutResourceId, categories);
        this.context = context;
        this.categories = categories;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        CategoryListAdapter.CategoryHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CategoryListAdapter.CategoryHolder();
            holder.name = row.findViewById(R.id.category_item_name);
            holder.image = row.findViewById(R.id.category_item_image);

            row.setTag(holder);
        }
        else{
            holder = (CategoryListAdapter.CategoryHolder) row.getTag();
        }

        Category category = categories.get(position);
        holder.name.setText(category.getName());
        try{
            holder.image.setImageResource(category.getImage());
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.category_filled50);
        }

        return row;
    }

    private class CategoryHolder{
        private ImageView image;
        private TextView name;

    }

}
