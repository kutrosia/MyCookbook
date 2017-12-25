package com.pwr.mycookbook.view.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.model.model.Category;
import com.pwr.mycookbook.viewmodel.CategoryViewModel;
import com.pwr.mycookbook.viewmodel.CategoryViewModelFactory;


import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoriesListFragment extends Fragment {

    public interface CategoryListListener{
        void categoryItemClicked(long category_id);
    }

    private ListView categories_list_view;
    private CategoriesListFragment.CategoryListListener listener;
    private CategoryListAdapter adapter;
    private List<Category> categories;
    private CategoryViewModel categoryViewModel;

    @Inject
    CategoryViewModelFactory factory;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        categories_list_view = view.findViewById(R.id.list_view_item);


        categoryViewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCategoriesList();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    Category categoryClicked = categories.get(position);
                    listener.categoryItemClicked(categoryClicked.getId());
                }
            }
        };
    }


    private void setCategoriesList() {

        categoryViewModel.getAll()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoriesList -> {
                    categories = categoriesList;
                    adapter = new CategoryListAdapter(getContext(), R.layout.list_category_item, categories);
                    categories_list_view.setAdapter(adapter);
                    categories_list_view.setOnItemClickListener(onItemClick());
                    adapter.notifyDataSetChanged();
                });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setCategoriesList();

    }

    /**support for device older than API 23*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (CategoriesListFragment.CategoryListListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        this.listener = (CategoriesListFragment.CategoryListListener)context;
    }
}
