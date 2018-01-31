package com.pwr.mycookbook.ui.user_profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

/**
 * Created by olaku on 17.12.2017.
 */

public class EditUserEmailActivity extends AppCompatActivity {

    private ImageView profile_photo;
    private TextView profile_name;
    private TextView profile_email;
    private EditText user_email_textEdit;
    private Button save_button;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;
    private SharedPreferences sharedPreferences;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_edit_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        profile_photo = findViewById(R.id.profileImageView);
        profile_name = findViewById(R.id.nameTxt);
        profile_email = findViewById(R.id.emailTxt);
        user_email_textEdit = findViewById(R.id.profil_edit_email_editText);
        save_button = findViewById(R.id.save_button);

        applyAvatar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        save_button.setOnClickListener(onSaveButtonClick());

        user_email_textEdit.setText(firebaseUser.getEmail());
        setUserOnToolbar();
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

    public void applyAvatar(){
        String gender = sharedPreferences.getString(SettingsActivity.KEY_GENDER, "");
        switch (gender){
            case "1":
                profile_photo.setImageResource(R.drawable.cook_white_grey100);
                break;
            case "2":
                profile_photo.setImageResource(R.drawable.chef100);
                break;
        }

    }

    private void setUserOnToolbar(){
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(EditUserEmailActivity.this, LoginActivity.class));
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
                String email = user_email_textEdit.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Podaj adres email", Toast.LENGTH_LONG).show();
                }else{
                    firebaseUser.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditUserEmailActivity.this, "Adres email został zaktualizowany. Zaloguj się ponownie używając nowego maila.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(EditUserEmailActivity.this, "Aktualizacja adresu email nie powiodła się", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    signOut();
                }
                }

            };
        }

    private void signOut() {
        firebaseAuth.signOut();
        finish();
    }

}
