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
import com.pwr.mycookbook.data.model.Category;
import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.service.AppDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class AddEditRecipeFragment extends Fragment implements IRecipeSave{
    private Recipe recipe;
    private AppDatabase db;
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
        db = AppDatabase.getAppDatabase(getContext());
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

            setItemsToSpinner();

            if(!recipe.isNew()){
                title = recipe.getTitle();
                time = String.valueOf(recipe.getTime());
                portion = String.valueOf(recipe.getPortion());

                try{
                    recipe_photo.setImageURI(Uri.fromFile(new File(recipe.getPhoto())));
                }catch (Exception e){
                    recipe_photo.setImageResource(R.drawable.restaurant_menu100);
                }

            }else{
                title = "";
                time = "0";
                portion = "0";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void setItemsToSpinner() {
        new AsyncTask<Object, Void, String>(){

            @Override
            protected String doInBackground(Object... params) {
                try {
                    categoryList = db.categoryDao().getAll();
                    List<String> catgoryTitles = new ArrayList<>();
                    for(Category category: categoryList){
                        catgoryTitles.add(category.getName());
                    }
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, catgoryTitles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }catch(Exception e){
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                category_spinner.setAdapter(adapter);
                category_spinner.setSelected(true);
            }
        }.execute();
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
        if(recipe != null){
            recipe.setTitle(title);
            recipe.setTime(Integer.parseInt(time));
            recipe.setPortion(Integer.parseInt(portion));
            if(category_pos >= 0){
                Category category = categoryList.get(category_pos);
                recipe.setCategory_id(category.getId());
            }
            ((IRecipeChange) getActivity()).setRecipeDetail(recipe);
        }
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        recipe_photo.setImageBitmap(bitmap);
    }
}
