package com.pwr.mycookbook.data.model_firebase;

import com.pwr.mycookbook.data.model_db.Category;

/**
 * Created by olaku on 09.01.2018.
 */

public class CategoryFb {

    private String key;
    private String name;
    private int image;
    private long modification_date;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void map(Category category){
        this.modification_date = category.getModification_date();
        this.image = category.getImage();
        this.name = category.getName();
        this.key = category.getKey();
    }
}
