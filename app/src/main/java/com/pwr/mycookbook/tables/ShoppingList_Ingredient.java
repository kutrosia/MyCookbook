package com.pwr.mycookbook.tables;

import android.arch.persistence.room.*;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "shoppinglists_ingredients", primaryKeys = {"shoppinglist_id", "ingredient_id"},
        foreignKeys = {
                        @ForeignKey(entity = ShoppingList.class,
                        parentColumns = "id", childColumns = "shoppinglist_id", onDelete = CASCADE),
                        @ForeignKey(entity = Ingredient.class,
                        parentColumns = "id", childColumns = "ingredient_id"),
        })

public class ShoppingList_Ingredient {

    public final long shoppinglist_id;

    public final long ingredient_id;

    public ShoppingList_Ingredient(long shoppinglist_id, long ingredient_id) {
        this.shoppinglist_id = shoppinglist_id;
        this.ingredient_id = ingredient_id;
    }

    @ColumnInfo(name = "quantity")
    private float quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "isToBuy")
    private boolean isToBuy;

    public long getShoppinglist_id() {
        return shoppinglist_id;
    }

    public long getIngredient_id() {
        return ingredient_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public boolean isToBuy() {
        return isToBuy;
    }

    public void setToBuy(boolean toBuy) {
        isToBuy = toBuy;
    }
}
