package com.pwr.mycookbook.data.model_firebase;

import android.graphics.Bitmap;

import com.pwr.mycookbook.data.model_db.Recipe;

/**
 * Created by olaku on 09.01.2018.
 */

public class RecipeFb {

    private String title;
    private int time;
    private int portion;
    private String category_key;
    private String description;
    private String note;
    private long modification_date;
    private String key;
    private String movie;

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public String getCategory_key() {
        return category_key;
    }

    public void setCategory_key(String category_key) {
        this.category_key = category_key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public void map(Recipe recipe){
        this.title = recipe.getTitle();
        this.category_key = recipe.getCategory_key();
        this.portion = recipe.getPortion();
        this.time = recipe.getTime();
        this.description = recipe.getDescription();
        this.note = recipe.getNote();
        this.movie = recipe.getMovie();
        this.key = recipe.getKey();
        this.modification_date = recipe.getModification_date();
    }
}
