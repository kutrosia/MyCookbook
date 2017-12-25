package com.pwr.mycookbook.view.recipe_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.model.repository.AppDatabase;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import java.io.File;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipeDetailFragment extends Fragment {
    private Recipe mRecipe;

    private static final String EXTRA_RECIPE = "recipe";
    private TextView recipe_title;
    private TextView recipe_category;
    private TextView recipe_time;
    private TextView recipe_portions;
    private ImageView photo;

    @Inject
    CategoryViewModelFactory categoryViewModelFactory;
    @Inject
    RecipeViewModelFactory recipeViewModelFactory;

    private RecipeViewModel recipeViewModel;
    private CategoryViewModel categoryViewModel;

    public static RecipeDetailFragment newInstance(long recipe_id) {
        RecipeDetailFragment result = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_RECIPE, recipe_id);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeViewModel = ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel.class);
        categoryViewModel = ViewModelProviders.of(this, categoryViewModelFactory).get(CategoryViewModel.class);
        Bundle bundle = this.getArguments();
        long recipe_id = bundle.getLong(EXTRA_RECIPE);
        getRecipeFromDB(recipe_id);

    }

    private void getRecipeFromDB(long recipe_id){
        recipeViewModel.findById(recipe_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe ->
                        mRecipe = recipe
                );
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

        setRecipeInfo();
    }


    public void setRecipeInfo(){
        if(mRecipe != null){
            recipe_title.setText(mRecipe.getTitle());

            getCategoryFromDb(mRecipe.getCategory_id());

            if(mRecipe.getTime() > 0)
                recipe_time.setText(String.valueOf(mRecipe.getTime()));

            if(mRecipe.getPortion() > 0)
                recipe_portions.setText(String.valueOf(mRecipe.getPortion()));

            if(mRecipe.getPhoto() != null){
                try {
                    photo.setImageURI(Uri.fromFile(new File(mRecipe.getPhoto())));
                }catch (Exception e){
                    Toast toast = Toast.makeText(getContext(), "Couldn't upload photo", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        }

    }

    private void getCategoryFromDb(long category_id){
        categoryViewModel.findById(category_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(category -> recipe_category.setText(category.getName()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRecipe != null){
            setRecipeInfo();
        }
    }

    public void setRecipe(Recipe mRecipe){
        this.mRecipe = mRecipe;
    }
}
