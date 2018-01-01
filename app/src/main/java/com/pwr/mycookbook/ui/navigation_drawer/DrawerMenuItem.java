package com.pwr.mycookbook.ui.navigation_drawer;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.user_profile.SynchronizationActivity;
import com.pwr.mycookbook.ui.user_profile.UserProfileActivity;

/**
 * Created by olaku on 06.12.2017.
 */

@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_RECIPES = 2;
    public static final int DRAWER_MENU_ITEM_SHOPPING_LISTS = 3;
    public static final int DRAWER_MENU_ITEM_PORTIONS_COUNTER = 4;
    public static final int DRAWER_MENU_ITEM_TIMER = 5;
    public static final int DRAWER_MENU_ITEM_VIDEOS = 6;
    public static final int DRAWER_MENU_ITEM_SYNC = 7;
    public static final int DRAWER_MENU_ITEM_OPTIONS = 8;

    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText(R.string.drawer_item_profile);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.user_filled50));
                break;
            case DRAWER_MENU_ITEM_RECIPES:
                itemNameTxt.setText(R.string.drawer_item_recipes);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.restaurant_menu20));
                break;
            case DRAWER_MENU_ITEM_SHOPPING_LISTS:
                itemNameTxt.setText(R.string.drawer_item_shopping_lists);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ingredients20));
                break;
            case DRAWER_MENU_ITEM_PORTIONS_COUNTER:
                itemNameTxt.setText(R.string.drawer_item_weight_counter);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.weight50));
                break;
            case DRAWER_MENU_ITEM_TIMER:
                itemNameTxt.setText(R.string.drawer_item_timer);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sand_timer_filled50));
                break;
            case DRAWER_MENU_ITEM_VIDEOS:
                itemNameTxt.setText(R.string.drawer_item_video);
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.play_button_filled50));
                break;
            case DRAWER_MENU_ITEM_SYNC:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.synchronize50));
                itemNameTxt.setText(R.string.drawer_item_sync);
                break;
            case DRAWER_MENU_ITEM_OPTIONS:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.services_filled50));
                itemNameTxt.setText(R.string.drawer_item_options);
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                Toast.makeText(mContext, "Profil", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext.getApplicationContext(), UserProfileActivity.class));
                if(mCallBack != null)mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_RECIPES:
                Toast.makeText(mContext, "Przepisy", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onRequestMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SHOPPING_LISTS:
                Toast.makeText(mContext, "Listy zakupów", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onGroupsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_PORTIONS_COUNTER:
                Toast.makeText(mContext, "Przelicznik porcji", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onMessagesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_TIMER:
                Toast.makeText(mContext, "Minutnik", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_VIDEOS:
                Toast.makeText(mContext, "Filmy", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SYNC:
                Toast.makeText(mContext, "Synchronizacja", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext.getApplicationContext(), SynchronizationActivity.class));
                if(mCallBack != null)mCallBack.onTermsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_OPTIONS:
                Toast.makeText(mContext, "Ustawienia", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onProfileMenuSelected();
        void onRequestMenuSelected();
        void onGroupsMenuSelected();
        void onMessagesMenuSelected();
        void onNotificationsMenuSelected();
        void onSettingsMenuSelected();
        void onTermsMenuSelected();
        void onLogoutMenuSelected();
    }
}
