package com.pwr.mycookbook.ui.shoppinglist_detail;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;
import com.pwr.mycookbook.ui.add_edit_shoppinglist.AddEditShoppinglistFragment;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

/**
 * Created by olaku on 07.01.2018.
 */

public class ShoppinglistDetailActivity extends AppCompatActivity
    implements ShoppinglistDetailFragment.ShoppinglistItemListener{

    public static final String EXTRA_SHOPPINGLIST = "shoppinglist";
    private ShoppingList shoppingList;
    private FragmentManager fm = getSupportFragmentManager();
    private ShoppinglistDetailFragment shoppinglistDetailFragment;
    private ShoppinglistRepository shoppinglistRepository;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_shoppinglist_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        shoppinglistRepository = new ShoppinglistRepository(getApplicationContext());
        shoppingList = (ShoppingList) getIntent().getExtras().get(EXTRA_SHOPPINGLIST);
        getSupportActionBar().setSubtitle(shoppingList.getName());

        shoppinglistDetailFragment = ShoppinglistDetailFragment.newInstance(shoppingList);
    }

    private void applyStyle() {
        String color = sharedPreferences.getString(SettingsActivity.KEY_APPEARANCE_COLOR, "");
        switch (color){
            case "1":
                getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case "2":
                getTheme().applyStyle(R.style.OverlayPrimaryColorGreen, true);
                break;
            case "3":
                getTheme().applyStyle(R.style.OverlayPrimaryColorBlue, true);
                break;
            case "4":
                getTheme().applyStyle(R.style.OverlayPrimaryColorRed, true);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shoppinglist_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit_shoppinglist:
                AddEditShoppinglistFragment addEditShoppinglistFragment = AddEditShoppinglistFragment.newInstance(shoppingList);
                addEditShoppinglistFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getSupportActionBar().setSubtitle(shoppingList.getName());
                        shoppinglistDetailFragment.onResume();
                    }
                });
                addEditShoppinglistFragment.show(fm, "Edytuja listę zakupów");
                break;
            case R.id.action_remove_shoppinglist:
                shoppinglistRepository.delete(shoppingList);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void itemClicked(ShoppingList_Ingredient shoppingList_ingredient) {

    }
}
