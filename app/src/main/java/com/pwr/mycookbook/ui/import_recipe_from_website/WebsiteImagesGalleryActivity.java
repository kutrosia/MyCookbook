package com.pwr.mycookbook.ui.import_recipe_from_website;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

/**
 * Created by olaku on 04.01.2018.
 */

public class WebsiteImagesGalleryActivity extends AppCompatActivity {

    private GridView images_grid_view;
    private WebsiteImagesGalleryAdapter adapter;
    public static final String EXTRA_URLS = "urls";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_website_image_gallery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        images_grid_view = findViewById(R.id.images_grid_view);

        String resurces = (String) getIntent().getExtras().get(EXTRA_URLS);
        String[] images = resurces.split(" ");

        adapter = new WebsiteImagesGalleryAdapter(images, R.layout.list_website_image_item, WebsiteImagesGalleryActivity.this);
        images_grid_view.setAdapter(adapter);
        images_grid_view.setOnItemClickListener(onImageClick());
        adapter.notifyDataSetChanged();

    }

    private void applyStyle() {
        String color = sharedPreferences.getString(SettingsActivity.KEY_APPEARANCE_COLOR, "");
        switch (color){
            case "1":
                getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case "2":
                getTheme().applyStyle(R.style.OverlayPrimaryColorGreen, true);
                break;
            case "3":
                getTheme().applyStyle(R.style.OverlayPrimaryColorBlue, true);
                break;
            case "4":
                getTheme().applyStyle(R.style.OverlayPrimaryColorRed, true);
                break;
        }
    }

    private AdapterView.OnItemClickListener onImageClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("img", adapter.getItem(i).toString());
                setResult(2, intent);
                finish();
            }
        };
    }


}
