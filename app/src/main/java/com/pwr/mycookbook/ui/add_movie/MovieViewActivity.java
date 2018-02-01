package com.pwr.mycookbook.ui.add_movie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.data.model_db.Recipe;
import com.pwr.mycookbook.ui.import_recipe_from_website.ImportRecipeFromWebsiteActivity;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import java.util.HashSet;

/**
 * Created by olaku on 01.02.2018.
 */

public class MovieViewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private SharedPreferences sharedPreferences;
    public static final String EXTRA_RECIPE = "recipe";
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_movies_view);

        recipe = (Recipe) getIntent().getExtras().get(EXTRA_RECIPE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.web_view);
        setWebViewSettings();
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

    private void setWebViewSettings() {
        if(recipe != null)
            url = recipe.getMovie();
        webView.setWebViewClient(new MovieViewActivity.MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            MovieViewActivity.this.url = url;
            view.loadUrl(url);
            return true;
        }
    }

    public class WebAppInterface
    {
        @JavascriptInterface
        public void callback(String value, int button)
        {
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();

        }
    }
}
