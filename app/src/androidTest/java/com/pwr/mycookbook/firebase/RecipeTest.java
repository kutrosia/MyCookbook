package com.pwr.mycookbook.firebase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.data.model_firebase.RecipeFb;
import com.pwr.mycookbook.data.service_db.AppDatabase;
import com.pwr.mycookbook.data.service_db.CategoryRepository;
import com.pwr.mycookbook.data.service_db.RecipeIngredientRepository;
import com.pwr.mycookbook.data.service_db.RecipeRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistIngredientRepository;
import com.pwr.mycookbook.data.service_db.ShoppinglistRepository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by olaku on 14.01.2018.
 */

public class RecipeTest {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private String user_email = "olakutrowska@gmail.com";
    private String user_password = "7127723";

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

    }

    @Test
    public void shouldCreateDatabase() {
        assertNotNull(db);
    }

    @Test
    public void shouldCreateRepositories() {
        assertNotNull(recipeRepository);

    }

    @Test
    public void shouldInsertRecipe() {
        RecipeFb recipeFb = new RecipeFb();
        recipeFb.setTitle("recipe_title");
        recipeFb.setDescription("description");
        int size = recipeRepository.getCount();
        recipeRepository.insertAll(recipe);
        List<Recipe> recipes = recipeRepository.getAll();
        int list_size = recipes.size();

        Assert.assertEquals(size + 1, recipes.size());
        Recipe dbRecipe = recipes.get(list_size-1);
        Assert.assertEquals(recipe.getTitle(), dbRecipe.getTitle());
        Assert.assertEquals(recipe.getDescription(), dbRecipe.getDescription());
    }

    @Test
    public void shouldDeleteRecipe() {
        Recipe recipe = new Recipe();
        int size = recipeRepository.getCount();
        recipe.setTitle("title");
        recipeRepository.insertAll(recipe);
        List<Recipe> recipes = recipeRepository.getAll();
        int list_size = recipes.size();
        Assert.assertEquals(size + 1, recipes.size());
        recipeRepository.deleteAll(recipes.get(list_size-1));
        recipes = recipeRepository.getAll();
        Assert.assertEquals(size, recipes.size());
    }

    @Test
    public void shouldUpdateRecipe() {
        //insert recipe
        Recipe recipe = new Recipe();
        recipe.setTitle("title");
        long id = recipeRepository.insertAll(recipe)[0];
        //find recipe by id
        Recipe recipeDb = recipeRepository.findById(id);
        //update recipe
        recipeDb.setTitle("title1");
        recipeRepository.updateAll(recipeDb);
        //find recipe again
        recipe = recipeRepository.findById(id);
        //check if updated
        Assert.assertEquals(recipe.getTitle(), "title1");
        //delete recipe
        recipeRepository.deleteAll(recipe);
        //check if deleted
        recipe = recipeRepository.findById(id);
        Assert.assertEquals(recipe, null);
    }
}
