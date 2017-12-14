package com.pwr.mycookbook.category_detail;

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
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.add_edit_recipe.AddEditRecipeFragment;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.lists.RecipeListAdapter;
import com.pwr.mycookbook.tables.*;

import java.util.List;

/**
 * Created by olaku on 24.11.2017.
 */

public class CategoryDetailFragment extends Fragment {

    interface RecipesListListener{
        void itemClicked(Recipe recipe);
    }

    public static final String EXTRA_CATEGORY = "category";
    private AppDatabase db;
    private Category category;
    private ListView recipes_list_view;
    private CategoryDetailFragment.RecipesListListener listener;
    private RecipeListAdapter adapter;
    private List<Recipe> recipes;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category_detail, container, false);
        db = AppDatabase.getAppDatabase(getContext());
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.category = (Category) bundle.getSerializable(EXTRA_CATEGORY);
        }
        recipes_list_view = view.findViewById(R.id.recepies_categorized_list_view);
        return view;

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    recipes = db.recipeDao().findAllByCategory(category.getId());
                    adapter = new RecipeListAdapter(getContext(), R.layout.list_recipe_item, recipes);

                }catch(Exception e){
                    return e.getMessage();
                }
                return "ok";
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                if(text.equals("ok")) {
                    recipes_list_view.setAdapter(adapter);
                    recipes_list_view.setOnItemClickListener(onItemClick());
                    adapter.notifyDataSetChanged();
                }

            }
        }.execute();


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
        this.listener = (CategoryDetailFragment.RecipesListListener)context;
    }


}
