package com.pwr.mycookbook.add_edit_recipe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pwr.mycookbook.R;

/**
 * Created by olaku on 11.12.2017.
 */

public class IngredientIconsListAdapter extends BaseAdapter {

    private Context context;
    private int layoutResourceId;
    private Integer[] icons;



    public IngredientIconsListAdapter(Context context, int layoutResourceId, Integer[] icons) {
        super();
        this.context = context;
        this.icons = icons;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int i) {
        return icons[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        IngredientIconsListAdapter.IconHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new IngredientIconsListAdapter.IconHolder();
            holder.image = row.findViewById(R.id.category_icon_item_image);
            row.setTag(holder);
        }else{
            holder = (IngredientIconsListAdapter.IconHolder) row.getTag();
        }
        holder.image.setImageResource(icons[position]);
        return row;
    }

    private class IconHolder{
        private ImageView image;

    }
}
