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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pwr.mycookbook.R;

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



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_photo = findViewById(R.id.profileImageView);
        profile_name = findViewById(R.id.nameTxt);
        profile_email = findViewById(R.id.emailTxt);
        user_email_textEdit = findViewById(R.id.profil_edit_email_editText);
        save_button = findViewById(R.id.save_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        save_button.setOnClickListener(onSaveButtonClick());

        user_email_textEdit.setText(firebaseUser.getEmail());
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
