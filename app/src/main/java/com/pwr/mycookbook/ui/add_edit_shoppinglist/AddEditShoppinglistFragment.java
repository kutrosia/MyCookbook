package com.pwr.mycookbook.ui.add_edit_shoppinglist;

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
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;

import java.util.Calendar;

/**
 * Created by olaku on 25.11.2017.
 */

public class AddEditShoppinglistFragment extends DialogFragment {

    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditIngredientTitle;
    private ShoppingList shoppinglist;
    private ShoppinglistRepository shoppinglistRepository;
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

    public AddEditShoppinglistFragment() {
    }

    public static AddEditShoppinglistFragment newInstance(ShoppingList shoppingList) {
        AddEditShoppinglistFragment frag = new AddEditShoppinglistFragment();
        if(shoppingList != null){
            Bundle args = new Bundle();
            args.putSerializable("shoppinglist", shoppingList);
            frag.setArguments(args);
        }
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        shoppinglistRepository = new ShoppinglistRepository(getContext());
        return inflater.inflate(R.layout.fragment_add_edit_ingredient, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            shoppinglist = (ShoppingList) getArguments().getSerializable("shoppinglist");
        }catch(NullPointerException e){
            Log.i("NullPointerException", e.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextInputLayout = view.findViewById(R.id.ingredient_name_TextInputLayout);
        if(shoppinglist != null)
            nameTextInputLayout.getEditText().setText(shoppinglist.getName());
        nameTextInputLayout.requestFocus();

        addEditIngredientTitle = view.findViewById(R.id.add_edit_ingredient_title);
        addEditIngredientTitle.setText(R.string.new_shoppinglist_title);
        if(shoppinglist !=null)
            addEditIngredientTitle.setText(R.string.edit_shoppinglist_title);

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
                AddEditShoppinglistFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onCreateButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextInputLayout.getEditText().getText().toString();
                Calendar rightNow = Calendar.getInstance();
                long currentTime = rightNow.getTimeInMillis();

                if(nameTextInputLayout.getEditText().getText() != null){
                    if(shoppinglist == null){
                        shoppinglist = new ShoppingList();
                        shoppinglist.setModification_date(currentTime);
                        shoppinglist.setName(name);
                        shoppinglistRepository.insertAll(shoppinglist);
                    }else{
                        shoppinglist.setName(name);
                        shoppinglist.setModification_date(currentTime);
                        shoppinglistRepository.update(shoppinglist);
                    }
                }
                AddEditShoppinglistFragment.this.dismiss();
            }
        };
    }
}
