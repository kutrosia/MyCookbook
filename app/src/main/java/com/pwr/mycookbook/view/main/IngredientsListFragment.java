package com.pwr.mycookbook.view.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Ingredient;
import com.pwr.mycookbook.viewmodel.IngredientViewModel;
import com.pwr.mycookbook.viewmodel.IngredientViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 25.11.2017.
 */

public class IngredientsListFragment extends Fragment {

    public interface IngredientsListListener{
        void ingredientItemClicked(long ingredient_id);
    }

    private ListView ingredients_list_view;
    private IngredientsListFragment.IngredientsListListener listener;
    private List<Ingredient> ingredientList;
    private IngredientsListAdapter adapter;

    @Inject
    IngredientViewModelFactory factory;
    private IngredientViewModel ingredientViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ingredients_list_view = view.findViewById(R.id.list_view_item);
        ingredientViewModel = ViewModelProviders.of(this, factory).get(IngredientViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setIngredientsList();
    }

    private void setIngredientsList(){
        ingredientViewModel.getAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredients -> {
                ingredientList = ingredients;
                adapter = new IngredientsListAdapter(getContext(), R.layout.list_ingredient_item, ingredientList);
                ingredients_list_view.setAdapter(adapter);
                ingredients_list_view.setOnItemClickListener(onItemClick());
                adapter.notifyDataSetChanged();});
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    Ingredient ingredientClicked = ingredientList.get(position);
                    listener.ingredientItemClicked(ingredientClicked.getId());
                }
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**support for device older than API 23*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (IngredientsListFragment.IngredientsListListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        this.listener = (IngredientsListFragment.IngredientsListListener)context;
    }


}
