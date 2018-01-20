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
import com.pwr.mycookbook.data.model_db.ShoppingList;

import java.util.List;

/**
 * Created by olaku on 05.12.2017.
 */

public class ShoppinglistListAdapter extends ArrayAdapter<ShoppingList> {

    private Context context;
    private int layoutResourceId;
    private List<ShoppingList> shoppingLists;



    public ShoppinglistListAdapter(Context context, int layoutResourceId, List<ShoppingList> shoppingLists) {
        super(context, layoutResourceId, shoppingLists);
        this.context = context;
        this.shoppingLists = shoppingLists;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ShoppinglistListAdapter.IngredientHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ShoppinglistListAdapter.IngredientHolder();
            holder.name = row.findViewById(R.id.shoppinglist_item_name);
            holder.image = row.findViewById(R.id.shoppinglist_item_image);

            row.setTag(holder);
        }
        else{
            holder = (ShoppinglistListAdapter.IngredientHolder) row.getTag();
        }

        ShoppingList shoppingList = shoppingLists.get(position);
        holder.name.setText(shoppingList.getName());
        try{
            //holder.image.setImageURI(Uri.fromFile(new File(ingredient.getPhoto())));
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.checklist50);
        }

        return row;
    }

    private class IngredientHolder{
        private ImageView image;
        private TextView name;

    }

}
