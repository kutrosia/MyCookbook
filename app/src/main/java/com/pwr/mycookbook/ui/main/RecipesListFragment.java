package com.pwr.mycookbook.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.service_db.RecipeRepository;

import java.util.List;


public class RecipesListFragment extends Fragment {

    public interface RecipesListListener{
        void recipeItemClicked(Recipe recipe);
    }
    private RecipeRepository recipeRepository;
    private ListView recipes_list_view;
    private RecipesListListener listener;
    private List<Recipe> recipeList;
    private RecipeListAdapter adapter;

    private Button category_filter_button;
    private Button name_filter_button;
    private Button time_filter_button;
    private EditText search_edit_text;
    private Button search_button;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        category_filter_button = view.findViewById(R.id.category_filter_button);
        name_filter_button = view.findViewById(R.id.name_filter_button);
        time_filter_button = view.findViewById(R.id.time_filter_button);
        search_edit_text = view.findViewById(R.id.search_edit_text);
        search_button = view.findViewById(R.id.search_button);
        recipes_list_view = view.findViewById(R.id.list_view_item);

        recipeRepository = new RecipeRepository(getContext());
        recipeList = recipeRepository.filterByTitle();
        setRecipeList();
        return view;
    }

    private void setRecipeList(){

        adapter = new RecipeListAdapter(getContext(), R.layout.list_recipe_item, recipeList);
        recipes_list_view.setAdapter(adapter);
        recipes_list_view.setOnItemClickListener(onItemClick());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        category_filter_button.setOnClickListener(onCategoryFilterClick());
        name_filter_button.setOnClickListener(onNameFilterClick());
        time_filter_button.setOnClickListener(onTimeFilterClick());
        search_edit_text.addTextChangedListener(onTextChange());
        search_button.setOnClickListener(onSearchButtonClick());
    }

    private View.OnClickListener onSearchButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

    private TextWatcher onTextChange() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sequence = "%" + charSequence.toString() + "%";
                recipeList = recipeRepository.getAllWithSubstring(sequence, sequence, sequence);
                if(recipeList.size() == 0){
                    recipeList = recipeRepository.getAllWithIngredients(sequence, sequence, sequence);
                }
                setRecipeList();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private View.OnClickListener onTimeFilterClick() {
        return new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        setRecipeList();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        recipeList = recipeRepository.filterByTime();
                        return null;
                    }
                }.execute();
            }
        };
    }

    private View.OnClickListener onNameFilterClick() {
        return new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        setRecipeList();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        recipeList = recipeRepository.filterByTitle();
                        return null;
                    }
                }.execute();
            }
        };
    }

    private View.OnClickListener onCategoryFilterClick() {
        return new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        setRecipeList();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        recipeList = recipeRepository.filterByCategory();
                        return null;

                    }
                }.execute();

            }
        };
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
        recipeList = recipeRepository.getAll();
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
