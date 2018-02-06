package com.pwr.mycookbook.ui.shoppinglist_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;

import java.util.List;

/**
 * Created by olaku on 07.01.2018.
 */

public class ShoppinglistDetailFragment extends Fragment {

    interface ShoppinglistItemListener {
        void itemClicked(ShoppingList_Ingredient shoppingList_ingredient);
    }

    public static final String EXTRA_SHOPPINGLIST = "shoppinglist";
    private ShoppinglistIngredientRepository shoppinglistIngredientRepository;
    private ShoppingList shoppingList;
    private ListView ingredients_list_view;
    private ShoppinglistDetailFragment.ShoppinglistItemListener listener;
    private ShoppinglistIngredientsListAdapter adapter;
    private List<ShoppingList_Ingredient> ingredients;
    private FloatingActionButton floatingActionButton;



    public static ShoppinglistDetailFragment newInstance(ShoppingList shoppingList)
    {
        ShoppinglistDetailFragment fragment = new ShoppinglistDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_SHOPPINGLIST, shoppingList);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shoppinglist_detail, container, false);
        shoppinglistIngredientRepository = new ShoppinglistIngredientRepository(getContext());
        ingredients_list_view = view.findViewById(R.id.ingredients_list_view);
        floatingActionButton = view.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(onFloatingButtonClick());

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            this.shoppingList = (ShoppingList) bundle.getSerializable(EXTRA_SHOPPINGLIST);
            try {
                ingredients = shoppinglistIngredientRepository.getIngredientsForShoppinglist(shoppingList.getId());
                adapter = new ShoppinglistIngredientsListAdapter(getContext(), R.layout.list_shoppinglist_ingredient_item, ingredients);
            }catch(Exception e){
                Log.e("CATEGORY DETAIL", e.getMessage());
            }
        }
        return view;

    }

    private View.OnClickListener onFloatingButtonClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AddShoppinglistIngredientFragment addShoppinglistIngredientFragment = AddShoppinglistIngredientFragment.newInstance(shoppingList);
                addShoppinglistIngredientFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ingredients = shoppinglistIngredientRepository.getIngredientsForShoppinglist(shoppingList.getId());
                        adapter = new ShoppinglistIngredientsListAdapter(getContext(), R.layout.list_shoppinglist_ingredient_item, ingredients);
                        ingredients_list_view.setAdapter(adapter);
                        ingredients_list_view.setOnItemClickListener(onItemClick());
                        adapter.notifyDataSetChanged();
                    }
                });
                addShoppinglistIngredientFragment.show(fm, "New ingredient");
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(shoppingList!=null){
            new AsyncTask<String, Void, String>(){

                @Override
                protected String doInBackground(String... params) {
                    try {
                        ingredients = shoppinglistIngredientRepository.getIngredientsForShoppinglist(shoppingList.getId());
                        adapter = new ShoppinglistIngredientsListAdapter(getContext(), R.layout.list_shoppinglist_ingredient_item, ingredients);
                    }catch(Exception e){
                        return e.getMessage();
                    }
                    return "ok";
                }

                @Override
                protected void onPostExecute(String text) {
                    super.onPostExecute(text);
                    if(text.equals("ok")) {
                        ingredients_list_view.setAdapter(adapter);
                        ingredients_list_view.setOnItemClickListener(onItemClick());
                        adapter.notifyDataSetChanged();
                    }

                }
            }.execute();
        }
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    listener.itemClicked(ingredients.get(position));
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**support for device older than API 23*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ShoppinglistDetailFragment.ShoppinglistItemListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ShoppinglistDetailFragment.ShoppinglistItemListener)context;
    }

}
