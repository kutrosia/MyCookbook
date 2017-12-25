package com.pwr.mycookbook.data;

import android.arch.persistence.room.*;
import android.content.Context;

import com.pwr.mycookbook.dao.CategoryDao;
import com.pwr.mycookbook.dao.IngredientDao;
import com.pwr.mycookbook.dao.RecipeDao;
import com.pwr.mycookbook.dao.Recipe_IngredientDao;
import com.pwr.mycookbook.dao.ShoppingListDao;
import com.pwr.mycookbook.dao.ShoppingList_IngredientDao;
import com.pwr.mycookbook.tables.Category;
import com.pwr.mycookbook.tables.Ingredient;
import com.pwr.mycookbook.tables.Recipe;
import com.pwr.mycookbook.tables.Recipe_Ingredient;
import com.pwr.mycookbook.tables.ShoppingList;
import com.pwr.mycookbook.tables.ShoppingList_Ingredient;

/**
 * Created by olaku on 03.12.2017.
 */
@Database(entities = {Recipe.class, Category.class, Ingredient.class,
        Recipe_Ingredient.class, ShoppingList.class,
        ShoppingList_Ingredient.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static final String DB_NAME = "recipes-database";
    public abstract RecipeDao recipeDao();
    public abstract CategoryDao categoryDao();
    public abstract IngredientDao ingredientDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract Recipe_IngredientDao recipe_ingredientDao();
    public abstract ShoppingList_IngredientDao shoppingList_ingredientDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

