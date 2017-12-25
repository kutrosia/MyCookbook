package com.pwr.mycookbook.view.add_edit_recipe;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 27.11.2017.
 */

public class AddEditRecipePreparationFragment extends Fragment implements IRecipeSave {

    private Recipe mRecipe;

    private static final String EXTRA_RECIPE = "recipe";
    private EditText recipe_description;
    private EditText recipe_note;
    private String description;
    private String note;

    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    private RecipeViewModel recipeViewModel;


    public static AddEditRecipePreparationFragment newInstance(long recipe_id) {
        AddEditRecipePreparationFragment result = new AddEditRecipePreparationFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_RECIPE, recipe_id);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeViewModel = ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel.class);
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
        return inflater.inflate(R.layout.fragment_add_edit_recipe_preparation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_description = view.findViewById(R.id.description_editText);
        recipe_note = view.findViewById(R.id.note_editText);


        setRecipeInfo();
    }


    public void setRecipeInfo(){
        if(!mRecipe.isNew()){
            description = mRecipe.getDescription();
            note = mRecipe.getNote();

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        description = recipe_description.getText().toString();
        note = recipe_note.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        recipe_description.setText(description);
        recipe_note.setText(note);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    public void setRecipe(Recipe recipe){
        this.mRecipe = recipe;
    }


    @Override
    public void saveRecipe() {
        mRecipe.setDescription(recipe_description.getText().toString());
        mRecipe.setNote(recipe_note.getText().toString());
        recipeViewModel.update(mRecipe);
        /*if(mRecipe != null){
            mRecipe.setDescription(recipe_description.getText().toString());
            mRecipe.setNote(recipe_note.getText().toString());
            ((IRecipeChange) getActivity()).setRecipeDescription(mRecipe);
        }*/
    }

    @Override
    public void setPhoto(Bitmap bitmap) {

    }
}
