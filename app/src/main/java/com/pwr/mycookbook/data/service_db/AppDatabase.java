package com.pwr.mycookbook.data.service_db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.*;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.pwr.mycookbook.data.dao.CategoryDao;
import com.pwr.mycookbook.data.dao.DatabaseDateDao;
import com.pwr.mycookbook.data.dao.RecipeDao;
import com.pwr.mycookbook.data.dao.Recipe_IngredientDao;
import com.pwr.mycookbook.data.dao.ShoppingListDao;
import com.pwr.mycookbook.data.dao.ShoppingList_IngredientDao;
import com.pwr.mycookbook.data.dao.TrashDao;
import com.pwr.mycookbook.data.model_db.Category;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_db.Recipe_Ingredient;
import com.pwr.mycookbook.data.model_db.ShoppingList;
import com.pwr.mycookbook.data.model_db.ShoppingList_Ingredient;
import com.pwr.mycookbook.data.model_db.DatabaseDate;
import com.pwr.mycookbook.data.model_db.Trash;

/**
 * Created by olaku on 03.12.2017.
 */
@Database(entities = {Recipe.class, Category.class,
        Recipe_Ingredient.class, ShoppingList.class,
        ShoppingList_Ingredient.class, DatabaseDate.class, Trash.class}, version = 14)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static final String DB_NAME = "recipes-database";
    public abstract RecipeDao recipeDao();
    public abstract CategoryDao categoryDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract Recipe_IngredientDao recipe_ingredientDao();
    public abstract ShoppingList_IngredientDao shoppingList_ingredientDao();
    public abstract DatabaseDateDao databaseDateDao();
    public abstract TrashDao trashDao();

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE recipes_ingredients "
                    + " ADD COLUMN name TEXT");
        }
    };

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            //.addMigrations(MIGRATION_5_6)
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

