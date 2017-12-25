package com.pwr.mycookbook.view.add_edit_category;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pwr.mycookbook.R;

/**
 * Created by olaku on 06.12.2017.
 */

public class CategoryIconsListAdapter extends BaseAdapter {

    private Context context;
    private int layoutResourceId;
    private Integer[] icons;



    public CategoryIconsListAdapter(Context context, int layoutResourceId, Integer[] icons) {
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
        CategoryIconsListAdapter.IconHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CategoryIconsListAdapter.IconHolder();
            holder.image = row.findViewById(R.id.category_icon_item_image);
            row.setTag(holder);
        }else{
            holder = (CategoryIconsListAdapter.IconHolder) row.getTag();
        }
        holder.image.setImageResource(icons[position]);
        return row;
    }

    private class IconHolder{
        private ImageView image;

    }
}
