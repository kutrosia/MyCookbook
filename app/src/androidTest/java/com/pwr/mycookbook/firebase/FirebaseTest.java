package com.pwr.mycookbook.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pwr.mycookbook.ui.user_profile.SignupActivity;
import com.pwr.mycookbook.ui.user_profile.UserProfileActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by olaku on 14.01.2018.
 */

public class FirebaseTest {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private Context context;

    @Before
    private void beforeTest(){
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Test
    private void shouldCreateUser(){
        String email = "";
        String password = "";
        firebaseAuth.createUserWithEmailAndPassword(email, password);
        firebaseUser  = firebaseAuth.getCurrentUser();
        assertNotNull(firebaseUser);
        Assert.assertEquals(firebaseUser.getEmail(), email);

    }

    @Test
    private void shouldChangeUserEmail(){

    }

    @Test
    private void shouldChangeUserPassword(){

    }

    @Test
    private void shouldDeleteUserAccount(){

    }
}
