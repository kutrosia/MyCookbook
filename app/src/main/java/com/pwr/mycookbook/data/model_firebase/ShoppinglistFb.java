package com.pwr.mycookbook.data.model_firebase;

import com.pwr.mycookbook.data.model_db.ShoppingList;

/**
 * Created by olaku on 09.01.2018.
 */

public class ShoppinglistFb {

    private String name;
    private long modification_date;
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void map(ShoppingList shoppinglist){
        this.name = shoppinglist.getName();
        this.key = shoppinglist.getKey();
        this.modification_date = shoppinglist.getModification_date();

    }
}
