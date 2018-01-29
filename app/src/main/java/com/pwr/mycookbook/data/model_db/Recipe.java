package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.pwr.mycookbook.data.model_firebase.RecipeFb;

import java.io.Serializable;

/**
 * Created by olaku on 02.12.2017.
 */


@Entity(tableName = "recipes")
public class Recipe implements Serializable {

    public Recipe() {
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "time")
    private int time;

    @ColumnInfo(name = "portion")
    private int portion;

    @ColumnInfo(name = "category_id")
    private long category_id;

    @ColumnInfo(name = "category_key")
    private String category_key;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "photo")
    private String photo;

    @Ignore
    private Bitmap photo_bitmap;

    @Ignore
    private boolean isNew;

    @Ignore
    private boolean isImported;

    @Ignore
    private String ingredients;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    public Recipe(long id, String title, int time, int portion, long category_id, String description, String note, String photo, Bitmap photo_bitmap, boolean isNew, long modification_date) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.portion = portion;
        this.category_id = category_id;
        this.description = description;
        this.note = note;
        this.photo = photo;
        this.photo_bitmap = photo_bitmap;
        this.isNew = isNew;
        this.modification_date = modification_date;
    }

    public Recipe(Recipe recipe){
        this.title = recipe.title;
        this.time = recipe.time;
        this.portion = recipe.portion;
        this.category_id = recipe.category_id;
        this.description = recipe.description;
        this.note = recipe.note;
        this.photo = recipe.photo;
        this.modification_date = recipe.modification_date;
    }

    public String getCategory_key() {
        return category_key;
    }

    public void setCategory_key(String category_key) {
        this.category_key = category_key;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isImported() {
        return isImported;
    }

    public void setImported(boolean imported) {
        isImported = imported;
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

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public Bitmap getPhoto_bitmap() {
        return photo_bitmap;
    }

    public void setPhoto_bitmap(Bitmap photo) {
        this.photo_bitmap = photo;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Ignore
    public void setRecipe(Recipe recipe){
        this.title = recipe.title;
        this.time = recipe.time;
        this.portion = recipe.portion;
        this.category_id = recipe.category_id;
        this.description = recipe.description;
        this.note = recipe.note;
        this.photo = recipe.photo;
        this.modification_date = recipe.modification_date;
    }

    public void map(RecipeFb recipe) {
        this.title = recipe.getTitle();
        this.category_key = recipe.getCategory_key();
        this.portion = recipe.getPortion();
        this.time = recipe.getTime();
        this.description = recipe.getDescription();
        this.note = recipe.getNote();
        this.photo = recipe.getPhoto();
        this.key = recipe.getKey();
        this.modification_date = recipe.getModification_date();
    }
}