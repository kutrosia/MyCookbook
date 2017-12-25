package com.pwr.mycookbook.user_profile;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.mycookbook.R;


/**
 * Created by olaku on 17.12.2017.
 */

public class UserActionsListAdapter extends BaseAdapter {

    private String[] titles;
    private Integer[] icons;
    private Context context;
    private Integer layoutResourceId;


    public UserActionsListAdapter(@NonNull Context context, int resource, String[] titles, Integer[]icons) {
        //super(context, resource);
        super();

        this.context = context;
        this.layoutResourceId = resource;
        this.titles = titles;
        this.icons = icons;

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //super.getView(position, convertView, parent);

        View row = convertView;
        UserActionsListAdapter.ActionHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserActionsListAdapter.ActionHolder();
            holder.title = row.findViewById(R.id.user_action_item_name);
            holder.image = row.findViewById(R.id.user_action_item_image);

            row.setTag(holder);
        }
        else {
            holder = (UserActionsListAdapter.ActionHolder) row.getTag();
        }

        String title = titles[position];
        Integer icon = icons[position];
        holder.title.setText(title);
        holder.image.setImageResource(icon);

        return row;
    }

    private class ActionHolder{
        private ImageView image;
        private TextView title;

    }

}
