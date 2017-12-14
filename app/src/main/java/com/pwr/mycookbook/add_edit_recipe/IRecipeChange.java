package com.pwr.mycookbook.add_edit_recipe;

import com.pwr.mycookbook.tables.Recipe;

/**
 * Created by olaku on 10.12.2017.
 */

public interface IRecipeChange {

    void setRecipeDetail(Recipe recipe);
    void setRecipeDescription(Recipe recipe);
}
