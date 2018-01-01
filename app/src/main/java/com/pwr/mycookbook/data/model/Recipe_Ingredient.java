package com.pwr.mycookbook.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "recipes_ingredients",
        primaryKeys = {"recipe_id", "ingredient_id"},
        foreignKeys = {
                        @ForeignKey(entity = Recipe.class,
                        parentColumns = "id", childColumns = "recipe_id", onDelete = CASCADE),
                        @ForeignKey(entity = Ingredient.class,
                        parentColumns = "id", childColumns = "ingredient_id")
        })
public class Recipe_Ingredient {

    public final long recipe_id;

    public final long ingredient_id;

    public Recipe_Ingredient(long recipe_id, long ingredient_id) {
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
    }

    @ColumnInfo(name = "quantity")
    private double quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;


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

    public long getRecipe_id() {
        return recipe_id;
    }

    public long getIngredient_id() {
        return ingredient_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
