package com.pwr.mycookbook.data.model_firebase;

import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;

/**
 * Created by olaku on 09.01.2018.
 */

public class ShoppinglistIngredientFb {

    private String shoppinglist_key;
    private boolean isToBuy;
    private long modification_date;
    private String key;

    public String getShoppinglist_key() {
        return shoppinglist_key;
    }

    public void setShoppinglist_key(String shoppinglist_key) {
        this.shoppinglist_key = shoppinglist_key;
    }

    public boolean isToBuy() {
        return isToBuy;
    }

    public void setToBuy(boolean toBuy) {
        isToBuy = toBuy;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void map(ShoppingList_Ingredient shoppingList_ingredient){
        this.key = shoppingList_ingredient.getKey();
        this.modification_date = shoppingList_ingredient.getModification_date();
        this.isToBuy = shoppingList_ingredient.isToBuy();
        this.shoppinglist_key = shoppingList_ingredient.getShoppinglist_key();
    }
}
