package com.pwr.mycookbook.add_edit_recipe;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pwr.mycookbook.lists.PagerAdapter;
import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.AppDatabase;
import com.pwr.mycookbook.tables.*;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class AddEditRecipeActivity extends AppCompatActivity implements IPickResult, IRecipeChange {
    public static final String EXTRA_RECIPE = "recipe";
    private Recipe recipe;
    private AppDatabase db;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private AddEditRecipeFragment addEditRecipeFragment;
    private AddEditRecipeIngredientsFragment addEditRecipeIngredientsFragment;
    private AddEditRecipePreparationFragment addEditRecipePreparationFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dossier_50));
        setSupportActionBar(toolbar);

        db = AppDatabase.getAppDatabase(getApplicationContext());

        recipe = (Recipe) getIntent().getExtras().get(EXTRA_RECIPE);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        addEditRecipeFragment = AddEditRecipeFragment.newInstance(recipe);
        addEditRecipeIngredientsFragment = AddEditRecipeIngredientsFragment.newInstance(recipe);
        addEditRecipePreparationFragment = AddEditRecipePreparationFragment.newInstance(recipe);
        pagerAdapter.addFragment(addEditRecipeFragment, "Szczegóły");
        pagerAdapter.addFragment(addEditRecipeIngredientsFragment, "Składniki");
        pagerAdapter.addFragment(addEditRecipePreparationFragment, "Instrukcje");

        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_add_edit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add_photo:
                showImageDialog();
                break;
            case R.id.action_save:
                addEditRecipeFragment.saveRecipe();
                addEditRecipePreparationFragment.saveRecipe();
                if(recipe.getTitle().equals("")){
                    Toast.makeText(getApplicationContext(), "Przepis musi mieć tytuł", Toast.LENGTH_LONG).show();
                }else{
                    insertOrUpdateRecipeToDb();
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void insertOrUpdateRecipeToDb(){
            if (recipe.isNew()) {
                db.recipeDao().insertAll(recipe);
            } else {
                db.recipeDao().updateAll(recipe);
            }
    }


    private void showImageDialog() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(AddEditRecipeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(AddEditRecipeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            }
        }else{
            ActivityCompat.requestPermissions(AddEditRecipeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PackageManager pm = getPackageManager();

                    if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                        PickImageDialog.build(new PickSetup()).show(this);

                    } else {

                        Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            Bitmap bitmap = pickResult.getBitmap();

            OutputStream fOut = null;
            File file = new File(getAlbumStorageDir("/MyCookbook"), "IMG_" + System.currentTimeMillis() + ".jpg");
            String path = file.getPath();
            recipe.setPhoto(path);
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
            values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
            values.put("_data", file.getAbsolutePath());

            ContentResolver cr = getContentResolver();
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        }
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("ALBUM", "Directory not created");
        }
        return file;
    }


    @Override
    public void setRecipeDetail(Recipe recipe) {
        this.recipe.setTitle(recipe.getTitle());
        this.recipe.setCategory_id(recipe.getCategory_id());
        this.recipe.setPhoto(recipe.getPhoto());
        this.recipe.setTime(recipe.getTime());
        this.recipe.setPortion(recipe.getPortion());
    }

    @Override
    public void setRecipeDescription(Recipe recipe) {
        this.recipe.setDescription(recipe.getDescription());
        this.recipe.setNote(recipe.getNote());
    }
}
