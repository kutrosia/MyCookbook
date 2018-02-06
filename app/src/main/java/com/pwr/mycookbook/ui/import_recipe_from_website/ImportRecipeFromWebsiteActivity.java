package com.pwr.mycookbook.ui.import_recipe_from_website;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.file.BitmapSave;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.ui.add_edit_recipe.AddEditRecipeActivity;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;


/**
 * Created by olaku on 03.01.2018.
 */

public class ImportRecipeFromWebsiteActivity extends AppCompatActivity {

    private Recipe recipe;
    private WebView webView;
    private Button copy_recipe_title_button;
    private Button copy_recipe_ingredients_button;
    private Button copy_recipe_description_button;
    private Button copy_recipe_movie_button;
    private Button copy_recipe_image_button;
    private String url;
    private HashSet<String> src;
    private SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_import_recipe_from_website);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.web_view);
        copy_recipe_title_button = findViewById(R.id.copy_recipe_title);
        copy_recipe_ingredients_button = findViewById(R.id.copy_recipe_ingredients);
        copy_recipe_description_button = findViewById(R.id.copy_recipe_description);
        copy_recipe_movie_button = findViewById(R.id.copy_recipe_movie);
        copy_recipe_image_button = findViewById(R.id.copy_recipe_photo);

        setWebViewSettings();

        copy_recipe_title_button.setOnClickListener(onButtonClick());
        copy_recipe_ingredients_button.setOnClickListener(onButtonClick());
        copy_recipe_description_button.setOnClickListener(onButtonClick());
        copy_recipe_movie_button.setOnClickListener(onButtonClick());
        copy_recipe_image_button.setOnClickListener(onImageButtonClick());

        recipe = new Recipe();
    }

    private void applyStyle() {
        String color = sharedPreferences.getString(SettingsActivity.KEY_APPEARANCE_COLOR, "");
        switch (color){
            case "1":
                getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case "2":
                getTheme().applyStyle(R.style.OverlayPrimaryColorGreen, true);
                break;
            case "3":
                getTheme().applyStyle(R.style.OverlayPrimaryColorBlue, true);
                break;
            case "4":
                getTheme().applyStyle(R.style.OverlayPrimaryColorRed, true);
                break;
        }
    }

    private View.OnClickListener onImageButtonClick() {
        return  new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                src = new HashSet();
                new AsyncTask<Void, Void, String>(){
                    @Override
                    protected String doInBackground(Void... voids) {
                        Document doc;
                        String sources = "";
                        try {
                            doc = Jsoup.connect(url).get();
                            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                            for (Element image : images) {
                                src.add(image.attr("src"));
                            }

                            for(String img: src){
                                sources += img + " ";
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return sources;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Intent intent = new Intent(ImportRecipeFromWebsiteActivity.this, WebsiteImagesGalleryActivity.class);
                        intent.putExtra(WebsiteImagesGalleryActivity.EXTRA_URLS, s);
                        startActivityForResult(intent, 2);

                    }
                }.execute();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setWebViewSettings() {
        url = "http://www.google.com/";
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.copy_recipe_title:
                        Toast.makeText(getApplicationContext(), "Kopiowanie tytułu", Toast.LENGTH_LONG).show();
                        copySelectedTextToRecipe(1);
                        break;
                    case R.id.copy_recipe_ingredients:
                        Toast.makeText(getApplicationContext(), "Kopiowanie składników", Toast.LENGTH_LONG).show();
                        copySelectedTextToRecipe(2);
                        break;
                    case R.id.copy_recipe_description:
                        Toast.makeText(getApplicationContext(), "Kopiowanie sposobu przygotowania", Toast.LENGTH_LONG).show();
                        copySelectedTextToRecipe(3);
                        break;
                    case R.id.copy_recipe_movie:
                        Toast.makeText(getApplicationContext(), "Zapisywanie filmu z przepisem", Toast.LENGTH_LONG).show();
                        recipe.setMovie(url);
                        break;
                }

            }
        };
    }

    private void copySelectedTextToRecipe(int button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("(function f(){return window.getSelection().toString();})()",
                    new ValueCallback<String>()
                    {
                        @Override
                        public void onReceiveValue(String value)
                        {
                            String removed_newlines;
                            switch (button){
                                case 1:
                                    removed_newlines = value.replace("\\n", " ");
                                    value = removed_newlines.replaceAll("\"", "");
                                    recipe.setTitle(value);
                                    Toast.makeText(getApplicationContext(), "Dodano tytuł", Toast.LENGTH_LONG).show();
                                    break;
                                case 2:
                                    removed_newlines = value.replace("\\n", "@");
                                    value = removed_newlines.replaceAll("\"", "");
                                    String oldIngredients = recipe.getIngredients();
                                    if(oldIngredients == null)
                                        oldIngredients = "";
                                        recipe.setIngredients(oldIngredients + "@" + value);
                                    Toast.makeText(getApplicationContext(), "Dodano składniki", Toast.LENGTH_LONG).show();
                                    break;
                                case 3:
                                    removed_newlines = value.replace("\\n", " ");
                                    value = removed_newlines.replaceAll("\"", "");
                                    String oldDescription = recipe.getDescription();
                                    if(oldDescription == null)
                                        oldDescription = "";
                                        recipe.setDescription(oldDescription + " " + value);
                                    Toast.makeText(getApplicationContext(), "Dodano sposób przygotowania", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });

        }else{
            webView.addJavascriptInterface(new WebAppInterface(), "js");
            webView.loadUrl("javascript:js.callback(window.getSelection().toString(), " + button +")");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_import_from_website_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:
                recipe.setImported(true);
                Intent intent = new Intent(this, AddEditRecipeActivity.class);
                intent.putExtra(AddEditRecipeActivity.EXTRA_RECIPE, recipe);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    Bitmap bitmap = null;
                    String imageURL;
                    if (requestCode == 2 && data != null) {
                        imageURL = data.getStringExtra("img");
                        try {
                            InputStream in = new java.net.URL(imageURL).openStream();
                            bitmap = BitmapFactory.decodeStream(in);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    if(bitmap != null){
                        Toast.makeText(getApplicationContext(), "Zapisywanie obrazu...", Toast.LENGTH_LONG).show();
                        BitmapSave bs = new BitmapSave();
                        bs.saveBitmap(bitmap, getApplicationContext());
                        recipe.setPhoto(bs.getFilePath());
                    }
                }
            }.execute();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ImportRecipeFromWebsiteActivity.this.url = url;
            view.loadUrl(url);
            return true;
        }
    }

    public class WebAppInterface
    {
        @JavascriptInterface
        public void callback(String value, int button)
        {


        }
    }


}
