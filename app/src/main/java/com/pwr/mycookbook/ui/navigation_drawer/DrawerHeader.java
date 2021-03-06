package com.pwr.mycookbook.ui.navigation_drawer;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itextpdf.text.Paragraph;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.pwr.mycookbook.R;

/**
 * Created by olaku on 06.12.2017.
 */
@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    private String gender;

    public DrawerHeader(String gender) {
        this.gender = gender;
    }

    @Resolve
    private void onResolved() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            nameTxt.setText(user.getDisplayName());
            emailTxt.setText(user.getEmail());
        }
        switch (gender){
            case "1":
                profileImage.setImageResource(R.drawable.cook_white_grey100);
                break;
            case "2":
                profileImage.setImageResource(R.drawable.chef100);
                break;
        }

    }
}
