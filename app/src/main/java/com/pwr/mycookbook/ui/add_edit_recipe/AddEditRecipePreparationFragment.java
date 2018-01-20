package com.pwr.mycookbook.ui.add_edit_recipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;

/**
 * Created by olaku on 27.11.2017.
 */

public class AddEditRecipePreparationFragment extends Fragment implements IRecipeSave {

    private Recipe recipe;

    private static final String EXTRA_RECIPE = "recipe";
    private EditText recipe_description;
    private EditText recipe_note;
    private String description;
    private String note;


    public static AddEditRecipePreparationFragment newInstance(Recipe recipe) {
        AddEditRecipePreparationFragment result = new AddEditRecipePreparationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_RECIPE, recipe);
        result.setArguments(bundle);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        recipe = (Recipe) bundle.getSerializable(EXTRA_RECIPE);

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
        if(!recipe.isNew()){
            description = recipe.getDescription();
            note = recipe.getNote();

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

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }


    @Override
    public void saveRecipe() {
        if(recipe != null){
            recipe.setDescription(recipe_description.getText().toString());
            recipe.setNote(recipe_note.getText().toString());
            ((IRecipeChange) getActivity()).setRecipeDescription(recipe);
        }
    }

    @Override
    public void setPhoto(Bitmap bitmap) {

    }
}
