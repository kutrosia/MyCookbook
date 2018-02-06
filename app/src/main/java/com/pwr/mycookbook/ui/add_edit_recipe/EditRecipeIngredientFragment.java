package com.pwr.mycookbook.ui.add_edit_recipe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;

/**
 * Created by olaku on 03.02.2018.
 */

public class EditRecipeIngredientFragment extends DialogFragment {

    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditIngredientTitle;
    private Recipe_Ingredient recipe_ingredient;
    private RecipeIngredientRepository recipeIngredientRepository;
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    public EditRecipeIngredientFragment() {
    }

    public static EditRecipeIngredientFragment newInstance(Recipe_Ingredient recipe_ingredient) {
        EditRecipeIngredientFragment frag = new EditRecipeIngredientFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipe_ingredient", recipe_ingredient);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            recipe_ingredient = (Recipe_Ingredient) getArguments().getSerializable("recipe_ingredient");
        }catch(NullPointerException e){
            Log.e("NullPointerException", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipeIngredientRepository = new RecipeIngredientRepository(getContext());
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextInputLayout = view.findViewById(R.id.ingredient_name_TextInputLayout);

        nameTextInputLayout.requestFocus();
        nameTextInputLayout.getEditText().setText(recipe_ingredient.getName());

        addEditIngredientTitle = view.findViewById(R.id.add_edit_ingredient_title);
        addEditIngredientTitle.setText(R.string.edit_igredient_title);

        createButton = view.findViewById(R.id.create_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        createButton.setText("Zapisz");
        createButton.setOnClickListener(onCreateButtonClickListener());

        cancelButton.setText("Cofnij");
        cancelButton.setOnClickListener(onCancelButtonClickListener());

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);

    }

    private View.OnClickListener onCancelButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRecipeIngredientFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onCreateButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextInputLayout.getEditText().getText().toString();
                if(name.length() > 0){
                    recipe_ingredient.setName(name);
                    recipeIngredientRepository.update(recipe_ingredient);
                }
                EditRecipeIngredientFragment.this.dismiss();
            }
        };
    }
}

