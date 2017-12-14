package com.pwr.mycookbook.ingredient;

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
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.tables.*;

import java.io.Serializable;

/**
 * Created by olaku on 25.11.2017.
 */

public class AddEditIngredientFragment extends DialogFragment {

    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditIngredientTitle;
    private Ingredient ingredient;
    private AppDatabase db;
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

    public AddEditIngredientFragment() {
    }

    public static AddEditIngredientFragment newInstance(Ingredient ingredient) {
        AddEditIngredientFragment frag = new AddEditIngredientFragment();
        if(ingredient != null){
            Bundle args = new Bundle();
            args.putSerializable("ingredient", (Serializable) ingredient);
            frag.setArguments(args);
        }
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ingredient = (Ingredient) getArguments().getSerializable("ingredient");
        }catch(NullPointerException e){
            Log.e("NullPointerException", e.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextInputLayout = view.findViewById(R.id.ingredient_name_TextInputLayout);
        if(ingredient != null)
            nameTextInputLayout.getEditText().setText(ingredient.getName());
        nameTextInputLayout.requestFocus();

        addEditIngredientTitle = view.findViewById(R.id.add_edit_ingredient_title);
        addEditIngredientTitle.setText(R.string.new_ingredient_title);
        if(ingredient !=null)
            addEditIngredientTitle.setText(R.string.edit_ingredient_title);

        createButton = view.findViewById(R.id.create_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        createButton.setText("Utwórz");
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
                AddEditIngredientFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onCreateButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextInputLayout.getEditText().getText().toString();
                if(nameTextInputLayout.getEditText().getText() != null){
                    if(ingredient == null){
                        ingredient = new Ingredient();
                        ingredient.setName(name);
                        db.ingredientDao().insertAll(ingredient);
                    }else{
                        ingredient.setName(name);
                        db.ingredientDao().update(ingredient);
                    }
                }
                AddEditIngredientFragment.this.dismiss();
            }
        };
    }
}
