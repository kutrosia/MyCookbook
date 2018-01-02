package com.pwr.mycookbook.ui.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.pwr.mycookbook.R;

/**
 * Created by olaku on 01.01.2018.
 */

public class EditUserNameActivity extends AppCompatActivity {

    private ImageView profile_photo;
    private TextView profile_name;
    private TextView profile_email;
    private EditText user_name_textEdit;
    private Button save_button;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_photo = findViewById(R.id.profileImageView);
        profile_name = findViewById(R.id.nameTxt);
        profile_email = findViewById(R.id.emailTxt);
        user_name_textEdit = findViewById(R.id.profil_edit_name_editText);
        save_button = findViewById(R.id.save_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        save_button.setOnClickListener(onSaveButtonClick());


        user_name_textEdit.setText(firebaseUser.getDisplayName());
        setUserOnToolbar();
    }

    private void setUserOnToolbar(){
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(EditUserNameActivity.this, LoginActivity.class));
                    finish();
                }else{
                    profile_photo.setImageURI(user.getPhotoUrl());
                    profile_name.setText(user.getDisplayName());
                    profile_email.setText(user.getEmail());
                }
            }
        };
    }

    private View.OnClickListener onSaveButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_name_textEdit.getText().toString().trim();

                if (name.equals("")) {
                    user_name_textEdit.setError("Wprowadź nazwę użytkownika");
                    Toast.makeText(getApplicationContext(), "Nazwa użytkownika nie może być pusta", Toast.LENGTH_LONG).show();
                }else{
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    firebaseUser.updateProfile(profileUpdates);
                }

            }

        };
    }
}