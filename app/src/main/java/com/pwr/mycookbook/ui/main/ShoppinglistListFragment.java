package com.pwr.mycookbook.ui.main;

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
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;

import java.util.List;

/**
 * Created by olaku on 25.11.2017.
 */

public class ShoppinglistListFragment extends Fragment {

    public interface IngredientsListListener{
        void shoppinglistItemClicked(ShoppingList shoppingList);
    }

    private ShoppinglistRepository shoppinglistRepository;
    private ListView shoppinglist_list_view;
    private ShoppinglistListFragment.IngredientsListListener listener;
    private List<ShoppingList> shoppingLists;
    private ShoppinglistListAdapter adapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        shoppinglistRepository = new ShoppinglistRepository(getContext());
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppinglist_list_view = view.findViewById(R.id.list_view_item);
        new AsyncTask<Object, Void, String>(){

            @Override
            protected String doInBackground(Object... params) {
                try {
                    shoppingLists = shoppinglistRepository.getAll();
                    adapter = new ShoppinglistListAdapter(getContext(), R.layout.list_shoppinglist_item, shoppingLists);

                }catch(Exception e){
                    return e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String text) {
                super.onPostExecute(text);
                shoppinglist_list_view.setAdapter(adapter);
                shoppinglist_list_view.setOnItemClickListener(onItemClick());
            }
        }.execute(view.getContext());
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null){
                    ShoppingList shoppingListClicked = shoppingLists.get(position);
                    listener.shoppinglistItemClicked(shoppingListClicked);
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
        this.listener = (ShoppinglistListFragment.IngredientsListListener)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ShoppinglistListFragment.IngredientsListListener)context;
    }


}
