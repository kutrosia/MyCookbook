package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.pwr.mycookbook.data.model_firebase.RecipeIngredientFb;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "recipes_ingredients",
        foreignKeys = {
                        @ForeignKey(entity = Recipe.class,
                        parentColumns = "id", childColumns = "recipe_id", onDelete = CASCADE)
        })
public class Recipe_Ingredient implements Serializable{

    public long recipe_id;

    @PrimaryKey(autoGenerate = true)
    public long ingredient_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "recipe_key")
    private String recipe_key;


    @Ignore
    private boolean isNew;

    public Recipe_Ingredient(long recipe_id, String name) {
        this.recipe_id = recipe_id;
        this.name = name;
    }

    @Ignore
    public Recipe_Ingredient(Recipe_Ingredient recipe_ingredient) {
        this.recipe_id = recipe_ingredient.recipe_id;
        this.name = recipe_ingredient.name;
        this.modification_date = recipe_ingredient.modification_date;
        this.key = recipe_ingredient.key;
    }

    @Ignore
    public Recipe_Ingredient() {
    }

    public String getRecipe_key() {
        return recipe_key;
    }

    public void setRecipe_key(String recipe_key) {
        this.recipe_key = recipe_key;
    }

    public void setIngredient_id(long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

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

    public long getIngredient_id() {
        return ingredient_id;
    }

    public long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(long recipe_id) {
        this.recipe_id = recipe_id;
    }

    @Ignore
    public void setRecipe_Ingredient(Recipe_Ingredient recipe_ingredient){
        this.name = recipe_ingredient.name;
        this.modification_date = recipe_ingredient.modification_date;
        this.key = recipe_ingredient.key;
    }

    public void map(RecipeIngredientFb recipe_ingredient){
        this.name = recipe_ingredient.getName();
        this.key = recipe_ingredient.getKey();
        this.modification_date = recipe_ingredient.getModification_date();
        this.recipe_key = recipe_ingredient.getRecipe_key();
    }
}
