package com.pwr.mycookbook.ui.add_edit_category;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.data.model.Category;

/**
 * Created by olaku on 23.11.2017.
 */

public class AddEditCategoryFragment extends DialogFragment {

    private TextInputLayout nameTextInputLayout;
    private Button createButton;
    private Button cancelButton;
    private TextView addEditCategoryTitle;
    private Spinner icons_spinner;
    private CategoryIconsListAdapter adapter;
    private Integer[] iconsRes;
    private Category category;
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

    public AddEditCategoryFragment() {
    }

    public static AddEditCategoryFragment newInstance(Category category) {
        AddEditCategoryFragment frag = new AddEditCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("category", category);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());
        return inflater.inflate(R.layout.fragment_add_edit_category, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            category = (Category) getArguments().getSerializable("category");
        }catch(NullPointerException e){
            Log.e("NullPointerException", e.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextInputLayout = view.findViewById(R.id.category_name_TextInputLayout);
        addEditCategoryTitle = view.findViewById(R.id.add_edit_category_title);
        createButton = view.findViewById(R.id.create_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        icons_spinner = view.findViewById(R.id.categories_icon_spinner);

        iconsRes = new Integer[]{
                R.drawable.noodles_black_white50,
                R.drawable.pizza50,
                R.drawable.rice_bowl_filled50,
                R.drawable.steak50,
                R.drawable.sushi50,
                R.drawable.food_and_wine50,
                R.drawable.cupcake_filled50,
                R.drawable.cake_filled50,
                R.drawable.pancake_filled50
        };

        adapter = new CategoryIconsListAdapter(getContext(), R.layout.list_category_icon_item, iconsRes);
        icons_spinner.setAdapter(adapter);

        if(category!= null)
            nameTextInputLayout.getEditText().setText(category.getName());
        nameTextInputLayout.requestFocus();

        addEditCategoryTitle.setText("Nowa kategoria");
        if(category!=null)
            addEditCategoryTitle.setText("Edytuj kategorię");

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
                AddEditCategoryFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onCreateButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextInputLayout.getEditText().getText().toString();
                if(nameTextInputLayout.getEditText().getText() != null){
                    if(category == null){
                        category = new Category();
                        category.setName(name);
                        category.setImage(iconsRes[icons_spinner.getSelectedItemPosition()]);
                        db.categoryDao().insertAll(category);
                    }else{
                        category.setName(name);
                        category.setImage(iconsRes[icons_spinner.getSelectedItemPosition()]);
                        db.categoryDao().update(category);
                    }
                }
                AddEditCategoryFragment.this.dismiss();
            }
        };
    }
}
