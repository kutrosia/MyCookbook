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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

/**
 * Created by olaku on 17.12.2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText email_editText;
    private EditText password_editText;
    private ImageView profile_photo;
    private Button login_button;
    private Button signup_button;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        profile_photo = findViewById(R.id.login_image);
        email_editText = findViewById(R.id.login_email_editText);
        password_editText = findViewById(R.id.login_password_editText);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);

        applyAvatar();

        signup_button.setOnClickListener(onSignupClick());
        login_button.setOnClickListener(onLoginButtonClikck());

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

    private void applyAvatar(){
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

    private View.OnClickListener onSignupClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private View.OnClickListener onLoginButtonClikck() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_editText.getText().toString().trim();
                final String password = password_editText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(firebaseAuth!=null)
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        password_editText.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        };
    }
}
