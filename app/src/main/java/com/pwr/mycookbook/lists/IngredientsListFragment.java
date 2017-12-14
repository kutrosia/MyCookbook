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
 * Created by olaku on 25.11.2017.
 */

public class IngredientsListFragment extends Fragment {

    public interface IngredientsListListener{
        void ingredientItemClicked(Ingredient ingredient);
    }

    private AppDatabase db;
    private ListView ingredients_list_view;
    private IngredientsListFragment.IngredientsListListener listener;
    private List<Ingredient> ingredientList;
    private IngredientsListAdapter adapter;

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

        ingredients_list_view = view.findViewById(R.id.list_view_item);
        new AsyncTask<Object, Void, String>(){

            @Override
            protected String doInBackground(Object... params) {
                try {
                    ingredientList = db.ingredientDao().getAll();
                    adapter = new IngredientsListAdapter(getContext(), R.layout.list_ingredient_item, ingredientList);

                }catch(Exception e){
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                ingredients_list_view.setAdapter(adapter);
                ingredients_list_view.setOnItemClickListener(onItemClick());
            }
        }.execute(view.getContext());
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    Ingredient ingredientClicked = ingredientList.get(position);
                    listener.ingredientItemClicked(ingredientClicked);
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
        this.listener = (IngredientsListFragment.IngredientsListListener)context;
    }


}
