package com.pwr.mycookbook.view.add_edit_ingredient;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.viewmodel.IngredientViewModel;
import com.pwr.mycookbook.viewmodel.IngredientViewModelFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 25.11.2017.
 */

public class AddEditIngredientFragment extends DialogFragment {

    private static final String EXTRA_INGREDIENT = "ingredient";
    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditIngredientTitle;
    private Ingredient ingredient;
    private DialogInterface.OnDismissListener onDismissListener;

    @Inject
    IngredientViewModelFactory ingredientViewModelFactory;
    private IngredientViewModel ingredientViewModel;

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

    public static AddEditIngredientFragment newInstance(long ingredient_id) {
        AddEditIngredientFragment frag = new AddEditIngredientFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_INGREDIENT, ingredient_id);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientViewModel = ViewModelProviders.of(this, ingredientViewModelFactory).get(IngredientViewModel.class);
        long ingredient_id = getArguments().getLong(EXTRA_INGREDIENT);
        getIngredientFromDb(ingredient_id);
    }

    private void getIngredientFromDb(long ingredient_id){
        ingredientViewModel.findById(ingredient_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredient1 -> ingredient = ingredient1);
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
                        ingredientViewModel.insert(ingredient);
                    }else{
                        ingredient.setName(name);
                        ingredientViewModel.update(ingredient);
                    }
                }
                AddEditIngredientFragment.this.dismiss();
            }
        };
    }
}
