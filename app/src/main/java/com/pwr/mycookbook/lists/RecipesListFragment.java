package com.pwr.mycookbook.lists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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


public class RecipesListFragment extends Fragment {

    public interface RecipesListListener{
        void recipeItemClicked(Recipe recipe);
    }
    private AppDatabase db;

    private ListView recipes_list_view;
    private RecipesListListener listener;
    private List<Recipe> recipeList;
    private RecipeListAdapter adapter;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recipes_list_view = view.findViewById(R.id.list_view_item);
        setRecipeList();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void setRecipeList(){

        recipeList = db.recipeDao().getAll();
        adapter = new RecipeListAdapter(getContext(), R.layout.list_recipe_item, recipeList);
        recipes_list_view.setAdapter(adapter);
        recipes_list_view.setOnItemClickListener(onItemClick());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    Recipe recipe = recipeList.get(position);
                    listener.recipeItemClicked(recipe);
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
        this.listener = (RecipesListListener)context;
    }

}
