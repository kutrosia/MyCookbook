package com.pwr.mycookbook.ui.measure_and_weight;

/**
 * Created by olaku on 29.01.2018.
 */

public class Product {

    private String name;
    private String spoon_value;
    private String glass_value;

    public Product(String name, String spoon_value, String glass_value) {
        this.name = name;
        this.spoon_value = spoon_value;
        this.glass_value = glass_value;
    }

    public String getName() {
        return name;
    }

    public String getSpoon_value() {
        return spoon_value;
    }

    public String getGlass_value() {
        return glass_value;
    }
}
