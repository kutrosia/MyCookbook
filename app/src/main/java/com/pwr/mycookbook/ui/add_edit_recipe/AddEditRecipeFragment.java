package com.pwr.mycookbook.ui.add_edit_recipe;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.service_db.CategoryRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class AddEditRecipeFragment extends Fragment implements IRecipeSave{
    private Recipe recipe;
    private CategoryRepository categoryRepository;
    private ImageView recipe_photo;
    private TextInputLayout title_TextInputLayout;
    private Spinner category_spinner;
    private ArrayAdapter<String> adapter;
    private TextInputLayout time_TextInputLayout;
    private TextInputLayout portions_TextInputLayout;
    private TextInputLayout url_movie_TextInputLayout;
    private List<Category> categoryList;


    private static final String EXTRA_RECIPE = "recipe";


    public AddEditRecipeFragment() {
        // Required empty public constructor
    }

    public static AddEditRecipeFragment newInstance(Recipe recipe) {
        AddEditRecipeFragment result = new AddEditRecipeFragment();
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
        this.recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);
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
            url_movie_TextInputLayout = view.findViewById(R.id.movie_url_TextInputLayout);

            setItemsToSpinner();

            showData();
        }
    }

    private void setItemsToSpinner() {

        categoryList = categoryRepository.getAll();
        List<String> catgoryTitles = new ArrayList<>();
        for(Category category: categoryList){
            catgoryTitles.add(category.getName());
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, catgoryTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(adapter);
        category_spinner.setSelected(true);

    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
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

    public void saveData(){
        recipe.setTitle(title_TextInputLayout.getEditText().getText().toString());
        int position = category_spinner.getSelectedItemPosition();
        Category category = getCategory(position);
        if(category != null)
            recipe.setCategory_id(category.getId());
        recipe.setTime(Integer.parseInt(time_TextInputLayout.getEditText().getText().toString()));
        recipe.setPortion(Integer.valueOf(portions_TextInputLayout.getEditText().getText().toString()));
        recipe.setMovie(url_movie_TextInputLayout.getEditText().getText().toString());
    }

    private void showData(){
        title_TextInputLayout.getEditText().setText(recipe.getTitle());
        category_spinner.setSelection(getCategoryPos(recipe.getCategory_id()));
        time_TextInputLayout.getEditText().setText(String.valueOf(recipe.getTime()));
        portions_TextInputLayout.getEditText().setText(String.valueOf(recipe.getPortion()));
        url_movie_TextInputLayout.getEditText().setText(recipe.getMovie());
        try{
            recipe_photo.setImageURI(Uri.fromFile(new File(recipe.getPhoto())));
        }catch (Exception e){
            recipe_photo.setImageResource(R.drawable.restaurant_menu100);
        }
    }

    private int getCategoryPos(long id){
        for(Category category: categoryList){
            if(category.getId() == id)
                return categoryList.indexOf(category);
        }
        return 0;
    }

    private Category getCategory(int position){
        if(categoryList.size() > 0)
            return categoryList.get(position);
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    public void saveRecipe() {
        saveData();
        ((IRecipeChange) getActivity()).setRecipeDetail(recipe);

    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        recipe_photo.setImageBitmap(bitmap);
    }
}
