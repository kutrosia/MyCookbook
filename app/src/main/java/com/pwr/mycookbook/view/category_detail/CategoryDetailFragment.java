package com.pwr.mycookbook.view.category_detail;

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
import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.model.model.Recipe;
import com.pwr.mycookbook.view.main.RecipeListAdapter;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;
import com.pwr.mycookbook.viewmodel.RecipeViewModel;
import com.pwr.mycookbook.viewmodel.RecipeViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoryDetailFragment extends Fragment {

    interface RecipesListListener{
        void itemClicked(Recipe recipe);
    }

    public static final String EXTRA_CATEGORY = "category";
    private Category category;
    private ListView recipes_list_view;
    private CategoryDetailFragment.RecipesListListener listener;
    private RecipeListAdapter adapter;
    private List<Recipe> recipes;

    @Inject
    CategoryViewModelFactory categoryViewModelFactory;
    @Inject
    RecipeViewModelFactory recipeViewModelFactory;
    private CategoryViewModel categoryViewModel;
    private RecipeViewModel recipeViewModel;


    public static CategoryDetailFragment newInstance(Long category_id)
    {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(EXTRA_CATEGORY, category_id);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category_detail, container, false);
        categoryViewModel = ViewModelProviders.of(this, categoryViewModelFactory).get(CategoryViewModel.class);
        recipeViewModel = ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel.class);
        recipes_list_view = view.findViewById(R.id.recepies_categorized_list_view);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            long category_id = bundle.getLong(EXTRA_CATEGORY);
            getCategoryFromDb(category_id);
        }
        return view;

    }

    private void getCategoryFromDb(long category_id){
        categoryViewModel.findById(category_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dbCategory -> category = dbCategory);
    }

    private void setRecipesList(){
        recipeViewModel.getAllByCategory(category.getId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes1 -> {
                    recipes = recipes1;
                    adapter = new RecipeListAdapter(getContext(), R.layout.list_recipe_item, recipes);
                    recipes_list_view.setAdapter(adapter);
                    recipes_list_view.setOnItemClickListener(onItemClick());
                    adapter.notifyDataSetChanged();
                });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecipesList();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    listener.itemClicked(recipes.get(position));
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
        this.listener = (CategoryDetailFragment.RecipesListListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        this.listener = (CategoryDetailFragment.RecipesListListener)context;
    }


}
