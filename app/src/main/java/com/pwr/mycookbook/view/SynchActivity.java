package com.pwr.mycookbook.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.pwr.mycookbook.R;

/**
 * Created by olaku on 17.12.2017.
 */

public class SynchActivity extends AppCompatActivity {

    private Switch sync_recipe_switch;
    private Switch sync_photo_switch;
    private Switch sync_movie_switch;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);

        sync_recipe_switch = findViewById(R.id.recipe_sync_switch);
        sync_photo_switch = findViewById(R.id.photo_sync_switch);
        sync_movie_switch = findViewById(R.id.movie_sync_switch);

        sync_recipe_switch.setOnClickListener(onRecipeSyncClick());
        sync_photo_switch.setOnClickListener(onPhotoSyncClick());
        sync_movie_switch.setOnClickListener(onMovieSyncClick());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();

        if(firebaseUser == null){
            sync_recipe_switch.setEnabled(false);
            sync_photo_switch.setEnabled(false);
            sync_movie_switch.setEnabled(false);
        }
    }

    private View.OnClickListener onMovieSyncClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

    private View.OnClickListener onPhotoSyncClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

    private View.OnClickListener onRecipeSyncClick() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
