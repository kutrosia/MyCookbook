package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.pwr.mycookbook.data.model_firebase.CategoryFb;

import java.io.Serializable;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "categories")
public class Category implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private int image;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    public Category(long id, String name, int image, long modification_date, String key) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.modification_date = modification_date;
        this.key = key;
    }

    public Category() {
    }

    public Category(Category category){
        this.name = category.name;
        this.image = category.image;
        this.modification_date = category.modification_date;
        this.key = category.key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getModification_date() {
        return modification_date;
    }

    public void setModification_date(long modification_date) {
        this.modification_date = modification_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setCategory(Category category){
        this.name = category.name;
        this.image = category.image;
        this.modification_date = category.modification_date;
        this.key = category.key;
    }

    public void map(CategoryFb categoryFb){
        this.name = categoryFb.getName();
        this.image = categoryFb.getImage();
        this.key = categoryFb.getKey();
        this.modification_date = categoryFb.getModification_date();
    }
}
