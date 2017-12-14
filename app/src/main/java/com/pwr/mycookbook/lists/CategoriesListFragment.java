package com.pwr.mycookbook.lists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.tables.*;

import java.util.List;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoriesListFragment extends Fragment {

    public interface CategoryListListener{
        void categoryItemClicked(Category category);
    }

    private AppDatabase db;
    private ListView categories_list_view;
    private CategoriesListFragment.CategoryListListener listener;
    private CategoryListAdapter adapter;
    private List<Category> categories;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = AppDatabase.getAppDatabase(getContext());
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories_list_view = view.findViewById(R.id.list_view_item);
        setCategoriesList();
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    Category categoryClicked = categories.get(position);
                    listener.categoryItemClicked(categoryClicked);
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
        setCategoriesList();

    }

    private void setCategoriesList() {
        categories = db.categoryDao().getAll();
        adapter = new CategoryListAdapter(getContext(), R.layout.list_category_item, categories);
        categories_list_view.setAdapter(adapter);
        categories_list_view.setOnItemClickListener(onItemClick());
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
        this.listener = (CategoriesListFragment.CategoryListListener)context;
    }
}
