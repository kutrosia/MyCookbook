package com.pwr.mycookbook.ui.measure_and_weight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olaku on 29.01.2018.
 */

public class ProductList {

    List<Product> products;

    public ProductList() {
        products = new ArrayList<>();
        products.add(new Product("bułka tarta", "7g", "120g"));
        products.add(new Product("cukier", "13g", "220g"));
        products.add(new Product("cukier puder", "10g", "170g"));
        products.add(new Product("kakao", "7,5g", "125g"));
        products.add(new Product("majonez", "12g", "200g"));
        products.add(new Product("masło", "15g", "240g"));
        products.add(new Product("mąka pszenna", "10g", "170g"));
        products.add(new Product("mąka ziemniaczana", "10g", "200g"));
        products.add(new Product("mąka żytnia", "8g", "140g"));
        products.add(new Product("mleko", "15g", "250g"));
        products.add(new Product("olej", "14g", "240g"));
        products.add(new Product("proszek do pieczenia", "9g", "150g"));
        products.add(new Product("ryż", "14g", "230g"));
        products.add(new Product("smalec", "11g", "210g"));
        products.add(new Product("sól", "18g", "220g"));
        products.add(new Product("śmietana", "13g", "220g"));
        products.add(new Product("woda", "15g", "250g"));
    }

    public List<Product> getProducts() {
        return products;
    }
}
