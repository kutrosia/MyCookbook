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
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RecipesListFragment extends Fragment {

    public interface RecipesListListener{
        void recipeItemClicked(long recipe_id);
    }

    private List<Recipe> recipesList;
    private ListView recipes_list_view;
    private RecipesListListener listener;
    private RecipeListAdapter adapter;

    @Inject
    RecipeViewModelFactory factory;
    private RecipeViewModel recipeViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recipes_list_view = view.findViewById(R.id.list_view_item);
        recipeViewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);
        return view;
    }

    private void setRecipeList(){
        recipeViewModel.getAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    recipesList = recipes;
                    if(adapter == null)
                        adapter = new RecipeListAdapter(getContext(), R.layout.list_recipe_item, recipes);
                    else
                        adapter.setRecipesList(recipes);
                    recipes_list_view.setOnItemClickListener(onItemClick());
                    recipes_list_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecipeList();


    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(listener!=null){
                    Recipe recipe = recipesList.get(position);
                    if(recipe != null)
                        listener.recipeItemClicked(recipe.getId());
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecipeList();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**support for device older than API 23*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (RecipesListListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        this.listener = (RecipesListListener)context;
    }

}
