package com.pwr.mycookbook.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;

import com.vansuita.pickimage.bundle.PickSetup;

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

    public long recipe_id;

    public long ingredient_id;

    public Recipe_Ingredient(long recipe_id, long ingredient_id) {
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
    }

    @Ignore
    public Recipe_Ingredient(double quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    @Ignore
    public Recipe_Ingredient() {
    }

    @Ignore


    @ColumnInfo(name = "quantity")
    private double quantity;

    @ColumnInfo(name = "measure")
    private String measure;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    @Ignore
    private boolean isNew;


    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
