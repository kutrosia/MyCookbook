package com.pwr.mycookbook.ui.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pwr.mycookbook.R;

/**
 * Created by olaku on 17.12.2017.
 */

public class UserProfileActivity extends AppCompatActivity {

        private ImageView profile_photo;
        private TextView profile_name;
        private TextView profile_email;
        private ListView actions_list;
        private UserActionsListAdapter adapter;
        private FirebaseAuth firebaseAuth;
        private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_photo = findViewById(R.id.profileImageView);
        profile_name = findViewById(R.id.nameTxt);
        profile_email = findViewById(R.id.emailTxt);
        actions_list = findViewById(R.id.user_actions_listView);

        String[] titles = new String[] {
                "Zmiana adresu e-mail",
                "Zmiana nazwy użytkownika",
                "Zmiana hasła",
                "Wyloguj",

        };

        Integer[] icons = new Integer[]{
                R.drawable.email50,
                R.drawable.user_male50,
                R.drawable.lock50,
                R.drawable.login_filled50
        };


        adapter = new UserActionsListAdapter(UserProfileActivity.this, R.layout.list_user_actions_item, titles, icons);

        actions_list.setAdapter(adapter);
        actions_list.setOnItemClickListener(onActionItemClick());

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                    finish();
                }else{
                    profile_photo.setImageURI(user.getPhotoUrl());
                    profile_name.setText(user.getDisplayName());
                    profile_email.setText(user.getEmail());
                }
            }
        };

    }

    private AdapterView.OnItemClickListener onActionItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Position: " + String.valueOf(position), Toast.LENGTH_LONG).show();

                switch (position){
                    case 0:
                        startActivity(new Intent(UserProfileActivity.this, EditUserEmailActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(UserProfileActivity.this, EditUserNameActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(UserProfileActivity.this, EditUserPasswordActivity.class));
                        break;
                    case 3:
                        firebaseAuth.signOut();
                        finish();
                        break;
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            firebaseAuth.removeAuthStateListener(authListener);
        }
    }
}
