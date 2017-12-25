package com.pwr.mycookbook.view.user_profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.pwr.mycookbook.R;

/**
 * Created by olaku on 17.12.2017.
 */

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText user_name_textEdit;
    private EditText user_email_textEdit;
    private EditText user_old_password_editText;
    private EditText user_new_password_editText;
    private Button save_button;
    private Button edit_photo_button;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user_name_textEdit = findViewById(R.id.profil_edit_name_editText);
        user_email_textEdit = findViewById(R.id.profil_edit_email_editText);
        user_old_password_editText = findViewById(R.id.profil_edit_old_password_editText);
        user_new_password_editText = findViewById(R.id.profil_edit_new_password_editText);
        save_button = findViewById(R.id.save_button);
        edit_photo_button = findViewById(R.id.user_photo_change_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        save_button.setOnClickListener(onSaveButtonClick());


        user_name_textEdit.setText(firebaseUser.getDisplayName());
        user_email_textEdit.setText(firebaseUser.getEmail());
    }

    private View.OnClickListener onSaveButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_name_textEdit.getText().toString().trim();
                String email = user_email_textEdit.getText().toString().trim();
                String oldPassword = user_old_password_editText.getText().toString().trim();
                String newPassword = user_new_password_editText.getText().toString().trim();
                if (name.equals("")) {
                    user_email_textEdit.setError("Wprowadź email");
                    Toast.makeText(getApplicationContext(), "Nazwa użytkownika nie może być pusta", Toast.LENGTH_LONG).show();
                }else{
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    firebaseUser.updateProfile(profileUpdates);
                    signOut();
                }

                if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Podaj adres email", Toast.LENGTH_LONG).show();
                }else{
                    firebaseUser.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditUserProfileActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(EditUserProfileActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    signOut();
                }
                if (newPassword.equals("")) {

                }else if(newPassword.length() < 6){
                    Toast.makeText(getApplicationContext(), "Nowe hasło musi mieć co najmniej 6 znaków", Toast.LENGTH_LONG).show();
                }else{
                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditUserProfileActivity.this, "Password is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                signOut();
                            } else {
                                Toast.makeText(EditUserProfileActivity.this, "Failed to update password!", Toast.LENGTH_LONG).show();
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
