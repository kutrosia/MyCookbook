package com.pwr.mycookbook.ui.add_movie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.service_db.RecipeRepository;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import java.util.List;

/**
 * Created by olaku on 01.02.2018.
 */

public class MoviesGalleryActivity extends AppCompatActivity {

    private GridView movies_grid_view;
    private MoviesGalleryAdapter adapter;
    private RecipeRepository recipeRepository;
    private List<Recipe> recipesWithMovie;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_movies_gallery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipeRepository = new RecipeRepository(getApplicationContext());
        recipesWithMovie = recipeRepository.getAllMovies();
        movies_grid_view = findViewById(R.id.movies_grid_view);
        adapter = new MoviesGalleryAdapter(recipesWithMovie, R.layout.list_movie_item, MoviesGalleryActivity.this);
        movies_grid_view.setAdapter(adapter);
        movies_grid_view.setOnItemClickListener(onMovieItemClick());
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

    private AdapterView.OnItemClickListener onMovieItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MoviesGalleryActivity.this, MovieViewActivity.class);
                intent.putExtra(MovieViewActivity.EXTRA_RECIPE, recipesWithMovie.get(i));
                startActivity(intent);
            }
        };
    }
}
