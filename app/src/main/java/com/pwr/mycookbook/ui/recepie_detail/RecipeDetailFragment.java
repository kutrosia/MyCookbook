package com.pwr.mycookbook.ui.recepie_detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.service_db.CategoryRepository;

import java.io.File;

public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private CategoryRepository categoryRepository;

    private static final String EXTRA_RECIPE = "recipe";
    private TextView recipe_title;
    private TextView recipe_category;
    private TextView recipe_time;
    private TextView recipe_portions;
    private TextView recipe_movie_url;
    private ImageView photo;


    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment result = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE, recipe);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryRepository = new CategoryRepository(getContext());
        Bundle bundle = this.getArguments();
        recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_title = view.findViewById(R.id.recipe_title);
        recipe_category = view.findViewById(R.id.recipe_category);
        recipe_time = view.findViewById(R.id.recipe_time);
        recipe_portions = view.findViewById(R.id.recipe_portions);
        photo = view.findViewById(R.id.recipe_photo);
        recipe_movie_url = view.findViewById(R.id.recipe_url_movie);

        setRecipeInfo();
    }


    public void setRecipeInfo(){
        if(recipe != null){
            recipe_title.setText(recipe.getTitle());

            Category category = categoryRepository.findById(recipe.getCategory_id());
            if(category!=null)
                recipe_category.setText(category.getName());

            if(recipe.getTime() > 0)
                recipe_time.setText(String.valueOf(recipe.getTime()));

            if(recipe.getPortion() > 0)
                recipe_portions.setText(String.valueOf(recipe.getPortion()));

            if(recipe.getPhoto() != null){
                try {
                    photo.setImageURI(Uri.fromFile(new File(recipe.getPhoto())));
                }catch (Exception e){
                    Toast toast = Toast.makeText(getContext(), "Couldn't upload photo", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            recipe_movie_url.setText(recipe.getMovie());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recipe != null){
            setRecipeInfo();
        }
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }
}
