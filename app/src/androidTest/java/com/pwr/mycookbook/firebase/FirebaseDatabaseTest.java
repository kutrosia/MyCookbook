package com.pwr.mycookbook.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pwr.mycookbook.data.model_firebase.RecipeFb;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by olaku on 28.01.2018.
 */

public class FirebaseDatabaseTest {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String user_email = "olakutrowska@gmail.com";
    private String user_password = "123456789";

    @Before
    public void beforeTest(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    assertNotNull(firebaseUser);
                    assertNotNull(firebaseDatabase);


                }
            });
        }else {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
    }

    @Test
    public void shouldUserBeActive(){
        assertNotNull(firebaseUser);
    }

    @Test
    public void shouldCreateInstanceToDatabase(){
        assertNotNull(firebaseDatabase);
    }

    @Test
    public void shouldCreateUserEndPoint(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        if(firebaseUser == null)
            firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    assertNotNull(firebaseUser);
                    if(firebaseUser != null){
                        DatabaseReference userEndPoint = firebaseDatabase.getReference(firebaseUser.getUid());
                        assertNotNull(userEndPoint);
                    }
                }
            });
        }else{
            DatabaseReference userEndPoint = firebaseDatabase.getReference(firebaseUser.getUid());
            assertNotNull(userEndPoint);
        }
    }

    @Test
    public void shouldWriteNewRecipe(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        if(firebaseUser == null)
            firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    assertNotNull(firebaseUser);
                    if(firebaseUser != null){
                        String title = "Szarlotka";
                        RecipeFb recipeFb = new RecipeFb();
                        recipeFb.setTitle(title);
                        DatabaseReference userEndPoint = firebaseDatabase.getReference(firebaseUser.getUid());
                        DatabaseReference recipesEndPoint = userEndPoint.child("recipes");
                        String key = recipesEndPoint.push().getKey();
                        recipeFb.setKey(key);
                        recipesEndPoint.child(key).setValue(recipeFb);

                        recipesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                assertEquals(dataSnapshot.getValue(RecipeFb.class).getTitle(), recipeFb.getTitle());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }else{
            String title = "Szarlotka";
            RecipeFb recipeFb = new RecipeFb();
            recipeFb.setTitle(title);
            DatabaseReference userEndPoint = firebaseDatabase.getReference(firebaseUser.getUid());
            DatabaseReference recipesEndPoint = userEndPoint.child("recipes");
            String key = recipesEndPoint.push().getKey();
            recipeFb.setKey(key);
            recipesEndPoint.child(key).setValue(recipeFb);

            recipesEndPoint.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    assertEquals(dataSnapshot.getValue(RecipeFb.class).getTitle(), recipeFb.getTitle());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
