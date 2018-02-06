package com.pwr.mycookbook.ui.shoppinglist_detail;

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
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;

/**
 * Created by olaku on 03.02.2018.
 */

public class AddShoppinglistIngredientFragment extends DialogFragment {

    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditIngredientTitle;
    private ShoppingList shoppinglist;
    private ShoppinglistIngredientRepository shoppinglistIngredientRepository;
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

    public AddShoppinglistIngredientFragment() {
    }

    public static AddShoppinglistIngredientFragment newInstance(ShoppingList shoppingList) {
        AddShoppinglistIngredientFragment frag = new AddShoppinglistIngredientFragment();
        Bundle args = new Bundle();
        args.putSerializable("shoppinglist", shoppingList);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            shoppinglist = (ShoppingList) getArguments().getSerializable("shoppinglist");
        }catch(NullPointerException e){
            Log.e("NullPointerException", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        shoppinglistIngredientRepository = new ShoppinglistIngredientRepository(getContext());
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextInputLayout = view.findViewById(R.id.ingredient_name_TextInputLayout);

        nameTextInputLayout.requestFocus();

        addEditIngredientTitle = view.findViewById(R.id.add_edit_ingredient_title);
        addEditIngredientTitle.setText(R.string.new_ingredient_title);

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
                AddShoppinglistIngredientFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onCreateButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextInputLayout.getEditText().getText().toString();
                if(name.length() > 0){
                    ShoppingList_Ingredient shoppingList_ingredient = new ShoppingList_Ingredient();
                    shoppingList_ingredient.setName(name);
                    shoppingList_ingredient.setToBuy(true);
                    shoppingList_ingredient.setShoppinglist_id(shoppinglist.getId());
                    shoppinglistIngredientRepository.insertAll(shoppingList_ingredient);
                }
                AddShoppinglistIngredientFragment.this.dismiss();
            }
        };
    }
}
