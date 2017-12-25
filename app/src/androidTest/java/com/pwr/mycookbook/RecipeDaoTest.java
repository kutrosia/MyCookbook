package com.pwr.mycookbook;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.pwr.mycookbook.model.dao.CategoryDao;
import com.pwr.mycookbook.model.dao.RecipeDao;
import com.pwr.mycookbook.model.repository.AppDatabase;
import com.pwr.mycookbook.model.model.Recipe;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by olaku on 03.12.2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDaoTest {

    /*private RecipeDao recipeDao;
    private CategoryDao categoryDao;
    private AppDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        recipeDao = db.recipeDao();
        categoryDao = db.categoryDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void shouldCreateDatabase() {
        assertNotNull(db);
    }

    @Test
    public void shouldCreateDao() {
        assertNotNull(recipeDao);
        assertNotNull(categoryDao);
    }

    @Test
    public void shouldInsertRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("recipe_title");
        recipe.setDescription("description");
        recipeDao.insertAll(recipe);
        List<Recipe> recipes = recipeDao.getAll();


        Assert.assertEquals(1, recipes.size());
        Recipe dbRecipe = recipes.get(0);
        Assert.assertEquals(recipe.getTitle(), dbRecipe.getTitle());
        Assert.assertEquals(recipe.getDescription(), dbRecipe.getDescription());
        Assert.assertEquals(1, dbRecipe.getId());
    }

    @Test
    public void shouldDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("title");
        recipeDao.insertAll(recipe);
        List<Recipe> recipes = recipeDao.getAll();

        Assert.assertEquals(1, recipes.size());
        recipeDao.deleteAll(recipes.get(0));
        recipes = recipeDao.getAll();
        Assert.assertEquals(0, recipes.size());
    }*/

}
