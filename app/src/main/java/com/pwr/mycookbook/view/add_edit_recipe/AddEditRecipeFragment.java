package com.pwr.mycookbook.view.add_edit_recipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */

public class AddEditRecipeFragment extends Fragment implements IRecipeSave{
    private Recipe mRecipe;
    private ImageView recipe_photo;
    private TextInputLayout title_TextInputLayout;
    private Spinner category_spinner;
    private ArrayAdapter<String> adapter;
    private TextInputLayout time_TextInputLayout;
    private TextInputLayout portions_TextInputLayout;
    private List<Category> categoryList;

    private String title;
    private int category_pos;
    private String time;
    private String portion;

    private static final String EXTRA_RECIPE = "recipe";

    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    @Inject
    CategoryViewModelFactory categoryViewModelFactory;
    private RecipeViewModel recipeViewModel;
    private CategoryViewModel categoryViewModel;

    public AddEditRecipeFragment() {
        // Required empty public constructor
    }

    public static AddEditRecipeFragment newInstance(long recipe_id) {
        AddEditRecipeFragment result = new AddEditRecipeFragment();
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
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view!=null){
            title_TextInputLayout = view.findViewById(R.id.title_TextInputLayout);
            category_spinner = view.findViewById(R.id.categories_spinner);
            time_TextInputLayout = view.findViewById(R.id.time_TextInputLayout);
            portions_TextInputLayout = view.findViewById(R.id.portions_TextInputLayout);
            recipe_photo = view.findViewById(R.id.recipe_photo);

            setItemsToSpinner();

            if(!mRecipe.isNew()){
                title = mRecipe.getTitle();
                time = String.valueOf(mRecipe.getTime());
                portion = String.valueOf(mRecipe.getPortion());
                try{
                    recipe_photo.setImageURI(Uri.fromFile(new File(mRecipe.getPhoto())));
                }catch (Exception e){
                    recipe_photo.setImageResource(R.drawable.dossier_100);
                }
            }else{
                title = "";
                time = "0";
                portion = "0";
            }
        }
    }

    private void setItemsToSpinner() {
        categoryViewModel.getAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    categoryList = categories;
                    List<String> catgoryTitles = new ArrayList<>();
                    for(Category category: categoryList){
                        catgoryTitles.add(category.getName());
                    }
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, catgoryTitles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category_spinner.setAdapter(adapter);
                    category_spinner.setSelected(true);
                });
    }

    public void setRecipe(Recipe recipe){
        this.mRecipe = recipe;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_recipe, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    public void saveData(){
        title = title_TextInputLayout.getEditText().getText().toString();
        category_pos = category_spinner.getSelectedItemPosition();
        time = time_TextInputLayout.getEditText().getText().toString();
        portion = portions_TextInputLayout.getEditText().getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        title_TextInputLayout.getEditText().setText(title);
        category_spinner.setSelection(category_pos);
        time_TextInputLayout.getEditText().setText(time);
        portions_TextInputLayout.getEditText().setText(portion);
    }

    @Override
    public void saveRecipe() {
        saveData();
        mRecipe.setTitle(title);
        mRecipe.setTime(Integer.parseInt(time));
        mRecipe.setPortion(Integer.parseInt(portion));
        if(category_pos >= 0){
            Category category = categoryList.get(category_pos);
            mRecipe.setCategory_id(category.getId());
        }
        recipeViewModel.update(mRecipe);
        /*if(mRecipe != null){
            mRecipe.setTitle(title);
            mRecipe.setTime(Integer.parseInt(time));
            mRecipe.setPortion(Integer.parseInt(portion));
            if(category_pos >= 0){
                Category category = categoryList.get(category_pos);
                mRecipe.setCategory_id(category.getId());
            }
            ((IRecipeChange) getActivity()).setRecipeDetail(mRecipe);
        }*/
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        recipe_photo.setImageBitmap(bitmap);
    }
}
