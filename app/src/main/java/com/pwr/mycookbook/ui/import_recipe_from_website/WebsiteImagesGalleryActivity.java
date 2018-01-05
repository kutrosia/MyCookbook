package com.pwr.mycookbook.ui.import_recipe_from_website;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.pwr.mycookbook.R;

/**
 * Created by olaku on 04.01.2018.
 */

public class WebsiteImagesGalleryActivity extends AppCompatActivity {

    private GridView images_grid_view;
    private WebsiteImagesGalleryAdapter adapter;
    public static final String EXTRA_URLS = "urls";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_image_gallery);

        images_grid_view = findViewById(R.id.images_grid_view);

        String resurces = (String) getIntent().getExtras().get(EXTRA_URLS);
        String[] images = resurces.split(" ");

        adapter = new WebsiteImagesGalleryAdapter(images, R.layout.list_website_image_item, WebsiteImagesGalleryActivity.this);
        images_grid_view.setAdapter(adapter);
        images_grid_view.setOnItemClickListener(onImageClick());
        adapter.notifyDataSetChanged();

    }

    private AdapterView.OnItemClickListener onImageClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), adapter.getItem(i).toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("img", adapter.getItem(i).toString());
                setResult(2, intent);
                finish();
            }
        };
    }


}
