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
import com.pwr.mycookbook.ui.user_profile.EditUserEmailActivity;
import com.pwr.mycookbook.ui.user_profile.SignupActivity;
import com.pwr.mycookbook.ui.user_profile.UserProfileActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by olaku on 14.01.2018.
 */

public class FirebaseAuthTest {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String user_email = "218156@student.pwr.edu.pl";
    private String user_password = "1234567890";

    @Before
    public void beforeTest(){
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            firebaseAuth.signOut();
        }
    }

    @Test
    public void shouldCreateAuthInstance(){
        assertNotNull(firebaseAuth);

    }

    @Test
    public void shouldCreateUser(){
        firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser  = firebaseAuth.getCurrentUser();
                        assertNotNull(firebaseUser);
                        Assert.assertEquals(firebaseUser.getEmail(), user_email);
                    }
                });
            }
        });
    }

    @Test
    public void shouldChangeUserEmail(){
        String newEmail = "olakutrowska@gmail.com";
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password);
            firebaseUser = firebaseAuth.getCurrentUser();
        }
        if(firebaseUser != null){
            firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Assert.assertEquals(firebaseUser.getEmail(), newEmail);
                }
            });

            firebaseUser.updateEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Assert.assertEquals(firebaseUser.getEmail(), user_email);
                }
            });
        }

    }

    @Test
    public void shouldChangeUserPassword(){
        String newPassword = "0987654321";
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password);
            firebaseUser = firebaseAuth.getCurrentUser();
        }
        if(firebaseUser != null){
            firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseAuth.signOut();
                    firebaseAuth.signInWithEmailAndPassword(user_email, newPassword);
                    firebaseUser = firebaseAuth.getCurrentUser();
                    assertNotNull(firebaseUser);
                }
            });

            firebaseUser.updatePassword(user_password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    firebaseAuth.signOut();
                    firebaseAuth.signInWithEmailAndPassword(user_email, user_password);
                    firebaseUser = firebaseAuth.getCurrentUser();
                    assertNotNull(firebaseUser);
                }
            });
        }
    }

    @Test
    public void shouldLogoutUser(){
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password);
            firebaseUser = firebaseAuth.getCurrentUser();
        }
        if(firebaseUser != null){
            firebaseAuth.signOut();
            assertNull(firebaseUser);
        }
    }

    @Test
    public void shouldDeleteUser(){
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    if(firebaseUser != null){
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                assertNull(firebaseUser);
                            }
                        });
                    }
                }
            });
        }
        if(firebaseUser != null){
            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    assertNull(firebaseUser);
                }
            });
        }
    }
}
