package com.pwr.mycookbook.ui.measure_and_weight;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.pwr.mycookbook.R;
import com.pwr.mycookbook.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaku on 29.01.2018.
 */

public class MeasureAndWeightActivity extends AppCompatActivity {

    private Spinner product_spinner;
    private TextView spoon_value;
    private TextView glass_value;
    private ProductList products;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyStyle();
        setContentView(R.layout.activity_measure_and_weight);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        product_spinner = findViewById(R.id.product_spinner);
        spoon_value = findViewById(R.id.spoon_value);
        glass_value = findViewById(R.id.glass_value);

        product_spinner.setOnItemSelectedListener(onItemSelection());

        products = new ProductList();
        List<String> products_name = new ArrayList<>();
        for(Product product: products.getProducts()){
            products_name.add(product.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.products_spinner_item, R.id.product_name, products_name);
        product_spinner.setAdapter(dataAdapter);

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

    private AdapterView.OnItemSelectedListener onItemSelection() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.getProducts().get(i);
                spoon_value.setText(product.getSpoon_value());
                glass_value.setText(product.getGlass_value());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }
}
