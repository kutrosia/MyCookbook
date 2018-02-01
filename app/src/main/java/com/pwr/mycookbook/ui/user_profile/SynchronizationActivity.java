package com.pwr.mycookbook.ui.user_profile;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.Trash;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.model_firebase.CategoryFb;
import com.pwr.mycookbook.data.model_firebase.RecipeFb;
import com.pwr.mycookbook.data.model_firebase.RecipeIngredientFb;
import com.pwr.mycookbook.data.model_firebase.ShoppinglistFb;
import com.pwr.mycookbook.data.model_firebase.ShoppinglistIngredientFb;
import com.pwr.mycookbook.data.service_db.CategoryRepository;
import com.pwr.mycookbook.data.service_db.DatabaseDateRepository;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;
import com.pwr.mycookbook.data.service_db.RecipeRepository;
import com.pwr.mycookbook.data.service_firebase.DataBasesSynchronization;
import com.pwr.mycookbook.data.service_firebase.RemoteDatabase;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;
import com.pwr.mycookbook.data.service_db.TrashRepository;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by olaku on 30.12.2017.
 */

public class SynchronizationActivity extends AppCompatActivity {


    private Button sync_button;
    private SharedPreferences sharedPreferences;
    private DataBasesSynchronization synchronization;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_synchronization);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        synchronization = new DataBasesSynchronization(getApplicationContext());
        sync_button = findViewById(R.id.sync_button);
        sync_button.setOnClickListener(onSyncButtonClick());

        if(synchronization.isNeeded()){
            sync_button.setEnabled(true);
        }else{
            sync_button.setEnabled(false);
        }
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


    private View.OnClickListener onSyncButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronization.synchronize();
                Toast.makeText(getApplicationContext(), "Synchronizacja baz danych...", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
