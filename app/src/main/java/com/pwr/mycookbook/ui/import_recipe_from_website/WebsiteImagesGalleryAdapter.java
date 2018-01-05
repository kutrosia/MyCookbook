package com.pwr.mycookbook.ui.import_recipe_from_website;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.pwr.mycookbook.R;

/**
 * Created by olaku on 04.01.2018.
 */

public class WebsiteImagesGalleryAdapter extends BaseAdapter {

    String[] src;
    int layoutResourceId;
    Context context;

    public WebsiteImagesGalleryAdapter(String[] src, int layoutResourceId, Context context) {
        super();
        this.src = src;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return src.length;
    }

    @Override
    public Object getItem(int i) {
        return src[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        WebsiteImagesGalleryAdapter.ImageHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WebsiteImagesGalleryAdapter.ImageHolder();
            holder.image = row.findViewById(R.id.webview_item);

            row.setTag(holder);
        }
        else{
            holder = (WebsiteImagesGalleryAdapter.ImageHolder) row.getTag();
        }

        try{
            //holder.image.getSettings().setLoadsImagesAutomatically(true);
            //holder.image.getSettings().setJavaScriptEnabled(true);
            //holder.image.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            holder.image.setClickable(false);
            holder.image.setFocusable(false);
            holder.image.setFocusableInTouchMode(false);
            /*holder.image.dispatchTouchEvent(null);
            holder.image.onTouchEvent(null);*/
            holder.image.loadDataWithBaseURL(null, "<html><head></head><body><table style=\"width:100%; height:100%;\"><tr><td style=\"vertical-align:middle;\"><img width=\"100dp\" height=\"100dp\" src=\"" + src[position] + "\"></td></tr></table></body></html>", "html/css", "utf-8", null);

        }catch (Exception e){

        }
        /*holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, src[position], Toast.LENGTH_LONG).show();
            }
        });*/

        return row;
    }

    private class ImageHolder{
        private WebView image;
    }
}
