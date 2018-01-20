package com.pwr.mycookbook.data.model_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.pwr.mycookbook.data.model_firebase.ShoppinglistFb;

import java.io.Serializable;

/**
 * Created by olaku on 02.12.2017.
 */

@Entity(tableName = "shoppinglists")
public class ShoppingList implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "modification_date")
    private long modification_date;

    @ColumnInfo(name = "key")
    private String key;

    public ShoppingList(ShoppingList shoppingList) {
        this.name = shoppingList.name;
        this.modification_date = shoppingList.modification_date;
        this.key = shoppingList.key;
    }

    public ShoppingList() {
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

    @Ignore
    public void setShoppingList(ShoppingList shoppingList){
        this.name = shoppingList.name;
        this.modification_date = shoppingList.modification_date;
        this.key = shoppingList.key;
    }

    public void map(ShoppinglistFb shoppinglist){
        this.name = shoppinglist.getName();
        this.key = shoppinglist.getKey();
        this.modification_date = shoppinglist.getModification_date();
    }
}
