package com.pwr.mycookbook.ui.add_edit_recipe;

import com.pwr.mycookbook.data.model.Recipe;
import com.pwr.mycookbook.data.model.Recipe_Ingredient;

import java.util.List;

/**
 * Created by olaku on 10.12.2017.
 */

public interface IRecipeChange {

    void setRecipeDetail(Recipe recipe);
    void setRecipeDescription(Recipe recipe);
    void setRecipeIngredients(List<Recipe_Ingredient> recipeIngredients);
}
