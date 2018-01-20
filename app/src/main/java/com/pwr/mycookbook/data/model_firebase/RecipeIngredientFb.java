package com.pwr.mycookbook.data.model_firebase;

import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;

/**
 * Created by olaku on 09.01.2018.
 */

public class RecipeIngredientFb {

    private String recipe_key;
    private String name;
    private long modification_date;
    private String key;

    public String getRecipe_key() {
        return recipe_key;
    }

    public void setRecipe_key(String recipe_key) {
        this.recipe_key = recipe_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void map(Recipe_Ingredient recipe_ingredient){
        this.name = recipe_ingredient.getName();
        this.key = recipe_ingredient.getKey();
        this.modification_date = recipe_ingredient.getModification_date();
        this.recipe_key = recipe_ingredient.getRecipe_key();
    }
}
