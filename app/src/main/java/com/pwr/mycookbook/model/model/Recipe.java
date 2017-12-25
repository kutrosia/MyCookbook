package com.pwr.mycookbook.model.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

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
}
