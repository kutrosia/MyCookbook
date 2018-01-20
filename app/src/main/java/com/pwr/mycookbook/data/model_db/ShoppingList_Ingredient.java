package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.*;

import com.pwr.mycookbook.data.model_firebase.ShoppinglistIngredientFb;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "shoppinglists_ingredients",
        foreignKeys = {
                        @ForeignKey(entity = ShoppingList.class,
                        parentColumns = "id", childColumns = "shoppinglist_id", onDelete = CASCADE)
        })

public class ShoppingList_Ingredient {

    public long shoppinglist_id;

    @PrimaryKey(autoGenerate = true)
    private long ingredient_id;

    @ColumnInfo(name = "isToBuy")
    private boolean isToBuy;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "shoppinglist_key")
    private String shoppinglist_key;

    public ShoppingList_Ingredient(long shoppinglist_id) {
        this.shoppinglist_id = shoppinglist_id;

    }

    @Ignore
    public ShoppingList_Ingredient() {
    }

    @Ignore
    public ShoppingList_Ingredient(ShoppingList_Ingredient shoppingList_ingredient) {
        this.shoppinglist_id = shoppingList_ingredient.shoppinglist_id;
        this.isToBuy = shoppingList_ingredient.isToBuy;
        this.modification_date = shoppingList_ingredient.modification_date;
        this.key = shoppingList_ingredient.key;
    }

    public String getShoppinglist_key() {
        return shoppinglist_key;
    }

    public void setShoppinglist_key(String shoppinglist_key) {
        this.shoppinglist_key = shoppinglist_key;
    }

    public void setIngredient_id(long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public long getShoppinglist_id() {
        return shoppinglist_id;
    }

    public long getIngredient_id() {
        return ingredient_id;
    }

    public boolean isToBuy() {
        return isToBuy;
    }

    public void setToBuy(boolean toBuy) {
        isToBuy = toBuy;
    }

    public void setShoppinglist_id(long shoppinglist_id) {
        this.shoppinglist_id = shoppinglist_id;
    }

    @Ignore
    public void setShoppinglistIngredient(ShoppingList_Ingredient shoppingList_ingredient){
        this.isToBuy = shoppingList_ingredient.isToBuy;
        this.modification_date = shoppingList_ingredient.modification_date;
        this.key = shoppingList_ingredient.key;
    }

    public void map(ShoppinglistIngredientFb shoppingList_ingredient){
        this.key = shoppingList_ingredient.getKey();
        this.modification_date = shoppingList_ingredient.getModification_date();
        this.isToBuy = shoppingList_ingredient.isToBuy();
        this.shoppinglist_key = shoppingList_ingredient.getShoppinglist_key();
    }
}
